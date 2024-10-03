package io.hhplus.cleancode.domain.mapper;

import io.hhplus.cleancode.domain.dto.SugangDto;
import io.hhplus.cleancode.domain.entity.Sugang;
import io.hhplus.cleancode.domain.entity.SugangHistory;
import io.hhplus.cleancode.domain.entity.SugangSchedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class SugangDtoMapper {

    @Autowired
    ModelMapper modelMapper;

//    public static List<SugangSchedule> toSugangSchedulesMapper(List<SugangDto> dtos) {
//        return dtos.stream()
//                .map(dto -> {
//                    SugangSchedule schedule = new SugangSchedule();
//
//                    schedule.setClassDate(dto.getClassDate());
//                    schedule.setAvailNum(dto.getAvailNum());
//                    schedule.setSugang(new Sugang(dto.getSugangId()));
////                    schedule.setStudent(new Student(dto.getStudentId()));
//                    return schedule;
//                })
//                .collect(Collectors.toList());
//    }



    public static List<SugangDto> scheduleToSugangDtoMapper(List<SugangSchedule> entities) {
        return entities.stream()
                .map( item -> {
                    SugangDto SugangDto = new SugangDto();

                    SugangDto.setClassDate(item.getClassDate());
                    SugangDto.setSugangId(item.getSugang().getSugangId());
                    SugangDto.setTeacher(item.getSugang().getTeacher());
                    SugangDto.setClassName(item.getSugang().getClassName());
                    SugangDto.setAvailNum(item.getAvailNum());

                    return SugangDto;
                })
                .collect(Collectors.toList());
    }
    
    public static List<SugangDto> historyToSugangDtoMapper(List<SugangHistory> entities) {
        return entities.stream()
                .map( item -> {
                    SugangDto SugangDto = new SugangDto();

                    SugangDto.setSugangId(item.getSugang().getSugangId());
                    SugangDto.setStudentId(item.getStudent().getStudentId());
                    SugangDto.setTeacher(item.getSugang().getTeacher());
                    SugangDto.setClassDate(item.getSugangSchedule().getClassDate());
                    SugangDto.setClassName(item.getSugang().getClassName());
                    SugangDto.setAvailNum(item.getSugangSchedule().getAvailNum());
                    return SugangDto;
                })
                .collect(Collectors.toList());
    }    
}
