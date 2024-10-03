package io.hhplus.cleancode;

import io.hhplus.cleancode.domain.dto.SugangDto;
import io.hhplus.cleancode.domain.repository.SugangScheduleRepository;
import io.hhplus.cleancode.domain.service.SugangService;
import io.hhplus.cleancode.infrastructure.repository.SugangHistoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CleanCodeIntegrationTests {

	@Autowired
	SugangService sugangService;

	@Autowired
	SugangScheduleRepository sugangScheduleRepository;
	
	@Autowired
	SugangHistoryJpaRepository sugangHistoryJpaRepository;

	@Test
	@DisplayName("5개 클라이언트에서 4번학생으로 동시에 1개 수강신청 가능한지")
	public void testConcurrency() throws InterruptedException {
		// 준비: 수강 정보 설정, 스레드 풀 생성
		SugangDto sugangDto = new SugangDto(1L,4L,0L,"20240801","1","1");
		int threadCount = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

		// 실행: 여러 스레드에서 동시에 수강 신청 시도
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					String result = sugangService.apply(sugangDto);
					System.out.println("Thread " + Thread.currentThread().getId() + ": " + result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		// 스레드 종료 대기
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

		// 검증: 한 명만 성공했는지 확인 (DB 조회 등을 통해 검증)
		Assertions.assertEquals(1,sugangHistoryJpaRepository.countBySugangSchedule_ClassDateAndStudent_StudentId("20240801",4L));
	}

	private static final AtomicInteger threadIdGenerator = new AtomicInteger(1);
	@Test
	@DisplayName("40명이 동시에 접속 ")
	void concurrentSugangTest() throws InterruptedException {
		// given
		int concurrentUsers = 40;
		Long sugangId = 5L;
		String classDate = "20240805";


		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(concurrentUsers);

		ExecutorService executorService = Executors.newFixedThreadPool(concurrentUsers);

		// when
		IntStream.range(1, concurrentUsers + 1).forEach(studentId -> {
			executorService.submit(() -> {
				try {
					int threadId = threadIdGenerator.getAndIncrement();
					startSignal.await();
//					new SugangDto(1L,4L,0L,"20240801","1","1");
					SugangDto sugangDto = new SugangDto(sugangId, (long) studentId, 0L, "20240805", "e", "jeong");
//					System.out.println("쓰레드 "+studentId);
					sugangService.apply(sugangDto);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					doneSignal.countDown();
				}
			});
		});

		startSignal.countDown(); // 모든 스레드 시작
		doneSignal.await(); // 모든 스레드 종료

		Assertions.assertEquals(30L,sugangHistoryJpaRepository.countBySugangSchedule_ClassDateAndSugang_SugangId("20240805",5L));
		// then
		// 수강 신청 성공 건수 확인 (예: 30건)
	}
}
