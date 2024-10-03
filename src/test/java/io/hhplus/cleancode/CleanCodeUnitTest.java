package io.hhplus.cleancode;

import io.hhplus.cleancode.domain.dto.SugangDto;
import io.hhplus.cleancode.domain.entity.SugangHistory;
import io.hhplus.cleancode.domain.entity.SugangSchedule;
import io.hhplus.cleancode.domain.repository.SugangHistoryRepository;
import io.hhplus.cleancode.domain.repository.SugangScheduleRepository;
import io.hhplus.cleancode.domain.service.SugangService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CleanCodeUnitTest {


    @InjectMocks
    private SugangService sugangService;

    @Mock
    private SugangScheduleRepository sugangScheduleRepository;

    @Mock
    private SugangHistoryRepository sugangHistoryRepository;

    @Test
    @DisplayName("잔여좌석이 있고, 신청내역이 없어 수강신청 성공")
    void apply_success() {
        // given
        SugangDto sugangInsertDto = new SugangDto(/* ... */);
        SugangSchedule sugangSchedule = new SugangSchedule(/* ... */);

        sugangSchedule.setAvailNum(30L);

        Mockito.when(sugangScheduleRepository.findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Optional.of(sugangSchedule));
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
//        Mockito.when(sugangHistoryRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Collections.emptyList());

//        Mockito.verify(sugangScheduleRepository, Mockito.never()).findBySugang_SugangIdAndClassDate(ArgumentMatchers.any(),ArgumentMatchers.any());
        Mockito.verify(sugangHistoryRepository, Mockito.never()).findAllBySugangSchedule_ClassDateAndStudent_StudentId(ArgumentMatchers.any(),ArgumentMatchers.any());
//        String result = sugangService.apply(sugangInsertDto);

        Assertions.assertThrows(DataAccessResourceFailureException.class, () -> sugangService.apply(sugangInsertDto));
//        Assertions.assertEquals("fail:no_seat", result);
        Mockito.verify(sugangScheduleRepository, Mockito.never()).save(sugangSchedule);
        Mockito.verify(sugangHistoryRepository, Mockito.never()).save(ArgumentMatchers.any(SugangHistory.class));
    }
}
