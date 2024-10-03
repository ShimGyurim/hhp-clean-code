package io.hhplus.cleancode.infrastructure.mapper;

import io.hhplus.cleancode.domain.entity.SugangHistory;
import io.hhplus.cleancode.infrastructure.entity.SugangEntity;
import io.hhplus.cleancode.infrastructure.entity.SugangHistoryEntity;
import io.hhplus.cleancode.infrastructure.entity.SugangScheduleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SugangHistoryMapper {

//    @Autowired
//    static ModelMapper modelMapper;
//
//    public  SugangHistoryEntity sugangHistoryToEntity  (SugangHistory sugangHistory) {
//        return modelMapper.map(sugangHistory,SugangHistoryEntity.class);
//    }
//
//    public  SugangHistory sugangHistoryToPojo (SugangHistoryEntity sugangHistoryEntity) {
//        return modelMapper.map(sugangHistoryEntity,SugangHistory.class);
//    }
//
//    public  List<SugangHistoryEntity> sugangHistoryToEntityList (List<SugangHistory> sugangHistories) {
//        return sugangHistories.stream()
//                .map(pojo -> {
//                    return modelMapper.map(pojo,SugangHistoryEntity.class);
//                })
//                .collect(Collectors.toList());
//    }
//
//    public  List<SugangHistory> sugangHistoryToPojoList (List<SugangHistoryEntity> sugangHistories) {
//        return sugangHistories.stream()
//                .map(item -> {
//                    return modelMapper.map(item,SugangHistory.class);
//                })
//                .collect(Collectors.toList());
//    }
}
