package io.hhplus.cleancode.infrastructure.mapper;

import io.hhplus.cleancode.domain.entity.Student;
import io.hhplus.cleancode.domain.entity.Sugang;
import io.hhplus.cleancode.domain.entity.SugangHistory;
import io.hhplus.cleancode.domain.entity.SugangSchedule;
import io.hhplus.cleancode.infrastructure.entity.StudentEntity;
import io.hhplus.cleancode.infrastructure.entity.SugangEntity;
import io.hhplus.cleancode.infrastructure.entity.SugangHistoryEntity;
import io.hhplus.cleancode.infrastructure.entity.SugangScheduleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DDDMapper {

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private SugangMapper sugangMapper;

//    @Autowired
//    private SugangScheduleEntity sugangScheduleEntity;

    public StudentEntity studentToEntity (Student student) {
        StudentEntity studentEntity = modelMapper.map(student,StudentEntity.class);
        studentEntity.setStudentId(student.getStudentId());
        return studentEntity;
    }

    public  Student studentToPojo (StudentEntity studentEntity) {
        Student student = modelMapper.map(studentEntity,Student.class);
        student.setStudentId(studentEntity.getStudentId());
        return student;
    }

    ////////////////////////////////////////////

    public SugangHistoryEntity sugangHistoryToEntity  (SugangHistory sugangHistory) {
        SugangHistoryEntity sugangHistoryEntity = modelMapper.map(sugangHistory,SugangHistoryEntity.class);

        sugangHistoryEntity.setHistoryId(sugangHistory.getHistoryId());
        return sugangHistoryEntity;
    }

    public  SugangHistory sugangHistoryToPojo (SugangHistoryEntity sugangHistoryEntity) {
        SugangHistory sugangHistory = modelMapper.map(sugangHistoryEntity,SugangHistory.class);
        sugangHistory.setHistoryId(sugangHistoryEntity.getHistoryId());
        return sugangHistory;
    }

    public List<SugangHistoryEntity> sugangHistoryToEntityList (List<SugangHistory> sugangHistories) {
        return sugangHistories.stream()
                .map(pojo -> {
                    return modelMapper.map(pojo,SugangHistoryEntity.class);
                })
                .collect(Collectors.toList());
    }

    public  List<SugangHistory> sugangHistoryToPojoList (List<SugangHistoryEntity> sugangHistories) {
        return sugangHistories.stream()
                .map(item -> {
                    return modelMapper.map(item,SugangHistory.class);
                })
                .collect(Collectors.toList());
    }

    //////////////////////////////////////


    public SugangEntity sugangToEntity  (Sugang sugang) {
        return modelMapper.map(sugang,SugangEntity.class);
    }

    public  Sugang sugangToPojo (SugangEntity sugangEntity) {
        return modelMapper.map(sugangEntity,Sugang.class);
    }

    /////////////////////////////////////////



    public  SugangScheduleEntity sugangScheduleToEntity (SugangSchedule sugangSchedule) {
        SugangScheduleEntity sugangScheduleEntity = modelMapper.map(sugangSchedule,SugangScheduleEntity.class);
        sugangScheduleEntity.setScheduleId(sugangSchedule.getScheduleId());
        return sugangScheduleEntity;
//        System.out.println("여기까지");
//        SugangScheduleEntity sugangScheduleEntity = new SugangScheduleEntity(
//                sugangSchedule.getScheduleId(),
//                sugangToEntity(sugangSchedule.getSugang()),
//                sugangSchedule.getClassDate(),
//                sugangSchedule.getAvailNum()
//        );
//        System.out.println("sugangScheduleEntity 변환");
//        return sugangScheduleEntity;
    }

    public  SugangSchedule sugangScheduleToPojo  (SugangScheduleEntity sugangScheduleEntity) {
        SugangSchedule sugangSchedule = modelMapper.map(sugangScheduleEntity,SugangSchedule.class);
        sugangSchedule.setScheduleId(sugangSchedule.getScheduleId());
        return sugangSchedule;
//        return new SugangSchedule(
//                sugangScheduleEntity.getScheduleId(),
//                sugangToPojo(sugangScheduleEntity.getSugang()),
//                sugangScheduleEntity.getClassDate(),
//                sugangScheduleEntity.getAvailNum()
//        );
    }


    public  List<SugangScheduleEntity> sugangScheduleToEntityList (List<SugangSchedule> list) {
        return list.stream()
                .map(item -> {
                    return modelMapper.map(item,SugangScheduleEntity.class);
                })
                .collect(Collectors.toList());
    }

    public  List<SugangSchedule> sugangScheduleToPojoList (List<SugangScheduleEntity> list) {
        return list.stream()
                .map(item -> {
                    return modelMapper.map(item,SugangSchedule.class);
                })
                .collect(Collectors.toList());
    }
}
