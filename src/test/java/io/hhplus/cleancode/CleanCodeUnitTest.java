package io.hhplus.cleancode;

import io.hhplus.cleancode.domain.dto.SugangDto;
import io.hhplus.cleancode.domain.entity.Student;
import io.hhplus.cleancode.domain.entity.Sugang;
import io.hhplus.cleancode.domain.entity.SugangHistory;
import io.hhplus.cleancode.domain.entity.SugangSchedule;
import io.hhplus.cleancode.domain.repository.SugangHistoryRepository;
import io.hhplus.cleancode.domain.repository.SugangScheduleRepository;
import io.hhplus.cleancode.domain.service.SugangService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class CleanCodeUnitTest {


    @InjectMocks
    private SugangService sugangService;

    @Mock
    private SugangScheduleRepository sugangScheduleRepository;

    @Mock
    private SugangHistoryRepository sugangHistoryRepository;

    @Mock
    private EntityManager entityManager;

    @Test
    @DisplayName("잔여좌석이 있고, 신청내역이 없어 수강신청 성공")
    void apply_success() {
        // given
        SugangDto sugangInsertDto = new SugangDto(/* ... */);
        SugangSchedule sugangSchedule = new SugangSchedule(/* ... */);

        sugangSchedule.setAvailNum(30L);

        Mockito.when(sugangScheduleRepository.findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.of(sugangSchedule));
        Mockito.when(sugangScheduleRepository.lockStart(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(sugangSchedule);
        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Collections.emptyList());

        // when
        String result = sugangService.apply(sugangInsertDto);

        // then
        Assertions.assertEquals("success", result);
        Mockito.verify(sugangScheduleRepository, Mockito.times(1)).save(sugangSchedule);
        Mockito.verify(sugangHistoryRepository, Mockito.times(1)).save(ArgumentMatchers.any(SugangHistory.class));
    }

    @Test
    @DisplayName("잔여좌석이 있지만, 신청내역이 있어 수강신청 실패")
    void apply_fail_already() {
        // given
        SugangDto sugangInsertDto = new SugangDto(/* ... */);
        SugangSchedule sugangSchedule = new SugangSchedule(/* ... */);

        sugangSchedule.setAvailNum(30L);

        Mockito.when(sugangScheduleRepository.findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.of(sugangSchedule));
        Mockito.when(sugangScheduleRepository.lockStart(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(sugangSchedule);
        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Collections.emptyList());

        List<SugangHistory> sugangHistoryList = new ArrayList<>();
        SugangHistory sugangHistory = new SugangHistory();
        sugangHistoryList.add(sugangHistory);

        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(sugangHistoryList);

        // when
        String result = sugangService.apply(sugangInsertDto);

        // then
        Assertions.assertEquals("fail:already", result);
        Mockito.verify(sugangScheduleRepository, Mockito.never()).save(sugangSchedule);
        Mockito.verify(sugangHistoryRepository, Mockito.never()).save(ArgumentMatchers.any(SugangHistory.class));
    }


    @Test
    @DisplayName("잔여좌석이 없어 수강신청 실패")
    void apply_success_no_seat() {
        // given
        SugangDto sugangInsertDto = new SugangDto(/* ... */);
        SugangSchedule sugangSchedule = new SugangSchedule(/* ... */);

        sugangSchedule.setAvailNum(0L);

        Mockito.when(sugangScheduleRepository.findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.of(sugangSchedule));
        Mockito.when(sugangScheduleRepository.lockStart(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(sugangSchedule);
//        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Collections.emptyList());

//        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Collections.emptyList());

        Mockito.verify(sugangHistoryRepository, Mockito.never()).findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(),ArgumentMatchers.any());
        // when
        String result = sugangService.apply(sugangInsertDto);

        // then
        Assertions.assertEquals("fail:no_seat", result);
        Mockito.verify(sugangScheduleRepository, Mockito.never()).save(sugangSchedule);
        Mockito.verify(sugangHistoryRepository, Mockito.never()).save(ArgumentMatchers.any(SugangHistory.class));
    }

    @Test
    @DisplayName("수강좌석정보가 없어 실패")
    void apply_success_no_seat_info() {
        // given
        SugangDto sugangInsertDto = new SugangDto(/* ... */);
        SugangSchedule sugangSchedule = new SugangSchedule(/* ... */);

        sugangSchedule.setAvailNum(null);

        Mockito.when(sugangScheduleRepository.findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.of(sugangSchedule));
//        Mockito.when(sugangScheduleRepository.lockStart(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(sugangSchedule);

//        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Collections.emptyList());

//        Mockito.verify(sugangScheduleRepository, Mockito.never()).findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(),ArgumentMatchers.any());
        Mockito.verify(sugangHistoryRepository, Mockito.never()).findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(),ArgumentMatchers.any());
//        String result = sugangService.apply(sugangInsertDto);

        Assertions.assertThrows(RuntimeException.class, () -> sugangService.apply(sugangInsertDto));
//        Assertions.assertEquals("fail:no_seat", result);
        Mockito.verify(sugangScheduleRepository, Mockito.never()).save(sugangSchedule);
        Mockito.verify(sugangHistoryRepository, Mockito.never()).save(ArgumentMatchers.any(SugangHistory.class));
    }

    @Test
    @DisplayName("Schedule 정보자체가 없어 실패")
    void apply_success_no_any_info() {
        // given
        SugangDto sugangInsertDto = new SugangDto(/* ... */);
        SugangSchedule sugangSchedule = new SugangSchedule(/* ... */);


        Mockito.when(sugangScheduleRepository.findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.empty());
//        Mockito.when(sugangScheduleRepository.lockStart(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(sugangSchedule);

//        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Collections.emptyList());

//        Mockito.verify(sugangScheduleRepository, Mockito.never()).findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(),ArgumentMatchers.any());
        Mockito.verify(sugangHistoryRepository, Mockito.never()).findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(),ArgumentMatchers.any());
//        String result = sugangService.apply(sugangInsertDto);

        Assertions.assertThrows(DataAccessResourceFailureException.class, () -> sugangService.apply(sugangInsertDto));
//        Assertions.assertEquals("fail:no_seat", result);
        Mockito.verify(sugangScheduleRepository, Mockito.never()).save(sugangSchedule);
        Mockito.verify(sugangHistoryRepository, Mockito.never()).save(ArgumentMatchers.any(SugangHistory.class));
    }

    @Test
    @DisplayName("일정한 강의를 조회 시 성공")
    public void getClassAvail_success() {
        // given
        // availNum은 입력에서 영향을 미치는 부분이 아니라 OL 로 입력
        SugangDto sugangInsertDto = new SugangDto(1L,0L,0L,"20240801","a","b");
        List<SugangSchedule> sugangScheduleList = new ArrayList<>();
        // 수강 일정 데이터 추가
        sugangScheduleList.add(new SugangSchedule(1L,new Sugang(1L,"a","b"),"20240801",30L));
        Mockito.when(sugangScheduleRepository.findAllByClassDateGreaterThanEqual(ArgumentMatchers.any())).thenReturn(sugangScheduleList);

        // when
        List<SugangDto> result = sugangService.getClassAvail(sugangInsertDto);

        // then
        Assertions.assertEquals(sugangScheduleList.size(), result.size());
        Assertions.assertEquals(sugangScheduleList.get(0).getSugang().getSugangId(),result.get(0).getSugangId());
        Assertions.assertEquals(sugangScheduleList.get(0).getSugang().getClassName(),result.get(0).getClassName());
        Assertions.assertEquals(sugangScheduleList.get(0).getSugang().getTeacher(),result.get(0).getTeacher());
        Assertions.assertEquals(sugangScheduleList.get(0).getClassDate(),result.get(0).getClassDate());
    }

    @Test
    @DisplayName("수강가능강의내역 조회 없음")
    public void getClassAvail_emptyResult() {
        // given
        SugangDto sugangInsertDto = new SugangDto(1L,0L,0L,"20240801","a","b");
        Mockito.when(sugangScheduleRepository.findAllByClassDateGreaterThanEqual(ArgumentMatchers.any())).thenReturn(Collections.emptyList());

        // when
        List<SugangDto> result = sugangService.getClassAvail(sugangInsertDto);

        // then
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("날짜별 조회성공")
    public void getClassApplyHistory_success() throws ParseException {
        // given
        SugangDto sugangInsertDto = new SugangDto(1L,1L,0L,"20240801","a","b");
        List<SugangHistory> sugangHistoryList = new ArrayList<>();
        // 수강 신청 이력 데이터 추가
        sugangHistoryList.add(new SugangHistory(1L
                        ,new SugangSchedule(1L
                            ,new Sugang(1L,"a","b")
                        ,"20240801",30L)
                 ,new Sugang(1L,"a","b")
            ,new Student(1L)));
        Mockito.when(sugangHistoryRepository.findAllByStudent_StudentId(ArgumentMatchers.any())).thenReturn(sugangHistoryList);

        // when
        List<SugangDto> result = sugangService.getClassApplyHistory(sugangInsertDto);

        // then
        Assertions.assertEquals(sugangHistoryList.size(), result.size());

        Assertions.assertEquals(sugangHistoryList.get(0).getSugang().getSugangId(),result.get(0).getSugangId());
        Assertions.assertEquals(sugangHistoryList.get(0).getSugang().getSugangId(),result.get(0).getSugangId());
        Assertions.assertEquals(sugangHistoryList.get(0).getSugang().getClassName(),result.get(0).getClassName());
        Assertions.assertEquals(sugangHistoryList.get(0).getSugang().getTeacher(),result.get(0).getTeacher());
        Assertions.assertEquals(sugangHistoryList.get(0).getSugangSchedule().getClassDate(),result.get(0).getClassDate());
    }


    @Test
    @DisplayName("날짜 없이 날짜조회")
    public void getClassApplyHistory_nullDate() {
        // given
        SugangDto sugangInsertDto = new SugangDto(1L,1L,0L,null,"a","b");

        // then
        Assertions.assertThrows(RuntimeException.class, () -> sugangService.getClassApplyHistory(sugangInsertDto));
        Mockito.verifyNoInteractions(sugangHistoryRepository); // 리포지토리가 호출되지 않았는지 검증
    }

    @Test
    @DisplayName("이상한 날짜로 조회")
    public void getClassApplyHistory_invalidDateFormat() {
        // given
        SugangDto sugangInsertDto = new SugangDto(1L,1L,0L,"19991436","a","b");

        // then
        Assertions.assertThrows(DateTimeException.class, () -> sugangService.getClassApplyHistory(sugangInsertDto));
        Mockito.verifyNoInteractions(sugangHistoryRepository); // 리포지토리가 호출되지 않았는지 검증
    }

//    public Date convertStringToDate(String dateString) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 8자리 날짜 형식 지정
//        return sdf.parse(dateString);
//    }

}
