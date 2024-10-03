package io.hhplus.cleancode.infrastructure.mapper;

import io.hhplus.cleancode.domain.entity.Sugang;
import io.hhplus.cleancode.domain.entity.SugangSchedule;
import io.hhplus.cleancode.infrastructure.entity.SugangEntity;
import io.hhplus.cleancode.infrastructure.entity.SugangScheduleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SugangScheduleMapper {
//
//    @Autowired
//    private  ModelMapper modelMapper;
//
//    @Autowired
//    private SugangMapper sugangMapper;
//
////    @Autowired
////    private SugangScheduleEntity sugangScheduleEntity;
//    // PR : 순환참조를 막는 프로그래밍 방법
//
//    public  SugangScheduleEntity sugangScheduleToEntity (SugangSchedule sugangSchedule) {
////        return modelMapper.map(sugangSchedule,SugangScheduleEntity.class);
//        System.out.println("여기까지");
//        SugangScheduleEntity sugangScheduleEntity = new SugangScheduleEntity(
//                sugangSchedule.getScheduleId(),
//                sugangMapper.SugangToEntity(sugangSchedule.getSugang()),
//                sugangSchedule.getClassDate(),
//                sugangSchedule.getAvailNum()
//        );
//        System.out.println("sugangScheduleEntity 변환");
//        return sugangScheduleEntity;
//    }
//
//    public  SugangSchedule sugangScheduleToPojo  (SugangScheduleEntity sugangScheduleEntity) {
////        return modelMapper.map(sugangScheduleEntity,SugangSchedule.class);
//        return new SugangSchedule(
//            sugangScheduleEntity.getScheduleId(),
//                SugangToPojo(sugangScheduleEntity.getSugang()),
//                sugangScheduleEntity.getClassDate(),
//                sugangScheduleEntity.getAvailNum()
//        );
//    }
//
//    public Sugang SugangToPojo (SugangEntity sugangEntity) {
//        return modelMapper.map(sugangEntity,Sugang.class);
//    }
//
//    public  List<SugangScheduleEntity> sugangScheduleToEntityList (List<SugangSchedule> list) {
//        return list.stream()
//                .map(item -> {
//                    return modelMapper.map(item,SugangScheduleEntity.class);
//                })
//                .collect(Collectors.toList());
//    }
//
//    public  List<SugangSchedule> sugangScheduleToPojoList (List<SugangScheduleEntity> list) {
//        return list.stream()
//                .map(item -> {
//                    return modelMapper.map(item,SugangSchedule.class);
//                })
//                .collect(Collectors.toList());
//    }
}
