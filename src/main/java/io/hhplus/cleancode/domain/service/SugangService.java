package io.hhplus.cleancode.domain.service;

import io.hhplus.cleancode.domain.dto.SugangDto;
import io.hhplus.cleancode.domain.entity.Student;
import io.hhplus.cleancode.domain.entity.Sugang;
import io.hhplus.cleancode.domain.entity.SugangHistory;
import io.hhplus.cleancode.domain.entity.SugangSchedule;
import io.hhplus.cleancode.domain.mapper.SugangDtoMapper;
import io.hhplus.cleancode.domain.repository.StudentRepository;
import io.hhplus.cleancode.domain.repository.SugangHistoryRepository;
import io.hhplus.cleancode.domain.repository.SugangRepository;
import io.hhplus.cleancode.domain.repository.SugangScheduleRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SugangService {

    @Autowired
    SugangRepository sugangRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SugangScheduleRepository sugangScheduleRepository;

    @Autowired
    SugangHistoryRepository sugangHistoryRepository;

    @Autowired
    EntityManager entityManager;

    private static final Logger log = LoggerFactory.getLogger(SugangService.class);

    @Transactional
    public String apply (SugangDto sugangInsertDto) {
        Optional<SugangSchedule> sugangScheduleOptional = Optional.ofNullable(sugangScheduleRepository.findBySugang_SugangIdAndClassDate(sugangInsertDto.getSugangId(), sugangInsertDto.getClassDate())
                .orElseThrow(() -> new DataAccessResourceFailureException("해당 수강 일정을 찾을 수 없습니다.")));
        SugangSchedule sugangScheduleOrigin = sugangScheduleOptional.get();

        if (sugangScheduleOrigin.getAvailNum() == null) {
            throw new RuntimeException("잔여좌석정보없음");
        }

        SugangSchedule sugangSchedule = sugangScheduleRepository.lockStart(sugangScheduleOrigin.getScheduleId(),entityManager);

        if(sugangSchedule == null) {
            entityManager.flush();
            throw new RuntimeException("entityManager 비정상작동");
        }
        if (sugangSchedule.getAvailNum() > 0) {


            List<SugangHistory> sugangHistoryList = sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(sugangInsertDto.getClassDate(),sugangInsertDto.getStudentId());

            if(sugangHistoryList != null && sugangHistoryList.size() > 0) {
                log.error("수강내역존재");
                entityManager.flush();
                return "fail:already"; // 해당 날짜 해당 강좌 신청 내역이 있음
                // TODO : 꼭 response 객체로 보내야하나 ?
            }
            System.out.println("수강내역없음");

            SugangHistory sugangHistory = new SugangHistory();

            sugangHistory.setSugangSchedule(sugangSchedule);
            sugangHistory.setSugang(sugangSchedule.getSugang());
            sugangHistory.setStudent(new Student(sugangInsertDto.getStudentId()));

            sugangSchedule.setAvailNum(sugangSchedule.getAvailNum() - 1); // 수강잔여좌석 갱신

            System.out.println("객체");
            try {
                sugangScheduleRepository.save(sugangSchedule);
                System.out.println("저장1개성공");
                sugangHistoryRepository.save(sugangHistory); //수강신청내역에 저장
                System.out.println("저장완료");
            }catch (Exception e){
                System.out.println("실패 발생");
                entityManager.flush();
                e.getStackTrace();
            }
            entityManager.flush();
            return "success";
        } else {
            entityManager.flush();
            return "fail:no_seat";
        }
    }

    public List<SugangDto> getClassAvail(SugangDto sugangInsertDto) {
        //schedule 에서 findByClassDate
        List<SugangSchedule> sugangScheduleList = sugangScheduleRepository.findAllByClassDateGreaterThanEqual(sugangInsertDto.getClassDate());

        return SugangDtoMapper.scheduleToSugangDtoMapper(sugangScheduleList);
    }

    public List<SugangDto> getClassApplyHistory(SugangDto sugangInsertDto) {
        //schedule 에서 findByClassDate

        if(sugangInsertDto.getClassDate() == null) {
            throw new RuntimeException("날짜가 없음");
        }
        String dateString = sugangInsertDto.getClassDate();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate localDate = LocalDate.parse(dateString, formatter);
        } catch (DateTimeException e) {
            throw new DateTimeException("날짜가 형식이 부정확");
        }

        log.error("로그:"+sugangInsertDto.getStudentId());
        List<SugangHistory> sugangScheduleList = sugangHistoryRepository.findAllByStudent_StudentId(sugangInsertDto.getStudentId());


        log.error("로그:"+sugangScheduleList.stream()
                .map(SugangHistory::toString)
                .collect(Collectors.toList()));
        if(sugangScheduleList == null) {
            throw new RuntimeException("리턴 객체가 없음");
        }else if(sugangScheduleList.size() == 0){
            return Collections.emptyList();
        }else if(sugangScheduleList.get(0).getSugang() == null) {
            throw new RuntimeException("강의 목록이 없음");
        }
        return SugangDtoMapper.historyToSugangDtoMapper(sugangScheduleList);
    }

}


