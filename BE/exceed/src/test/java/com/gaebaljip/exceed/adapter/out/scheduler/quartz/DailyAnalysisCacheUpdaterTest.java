package com.gaebaljip.exceed.adapter.out.scheduler.quartz;

import static com.gaebaljip.exceed.common.RedisKeys.NOW_ANALYSIS_CACHE_KEY;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gaebaljip.exceed.adapter.out.redis.RedisUtils;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetMonthlyAnalysisCommand;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetMonthlyAnalysisUsecase;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.RedisScanPattern;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;
import com.gaebaljip.exceed.common.dto.NowMonthAnalysisCache;

class DailyAnalysisCacheUpdaterTest extends IntegrationTest {

    @Autowired private DailyAnalysisCacheUpdater dailyAnalysisCacheUpdater;

    @Autowired private GetMonthlyAnalysisUsecase getMonthlyAnalysisUsecase;

    @Autowired private RedisUtils redisUtils;

    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.of(2024, 07, 01);
        Long memberId = 1L;
        String response =
                getMonthlyAnalysisUsecase.executeToNow(GetMonthlyAnalysisCommand.of(1L, date));
    }

    @AfterEach
    void tearDown() {
        redisUtils.flushDB();
    }

    @Test
    void test() {
        // given
        LocalDate date = LocalDate.of(2024, 07, 01);
        Long memberId = 1L;
        StringBuilder sb = new StringBuilder();
        String key =
                sb.append(NOW_ANALYSIS_CACHE_KEY)
                        .append(memberId)
                        .append("_")
                        .append(date)
                        .toString();
        NowMonthAnalysisCache before = NowMonthAnalysisCache.read(redisUtils.getData(key));

        LocalDate testDate = LocalDate.of(2024, 07, 07);
        CalorieAnalysisDTO calorieAnalysisDTO =
                CalorieAnalysisDTO.builder()
                        .date(testDate)
                        .isVisited(true)
                        .isCalorieAchieved(true)
                        .build();

        String pattern = RedisScanPattern.getAnalysisPattern(testDate);
        Set<String> keys = redisUtils.scanKeys(pattern, 10);

        // when
        dailyAnalysisCacheUpdater.updateCacheForKey(
                keys.stream().findAny().get(), calorieAnalysisDTO);

        // then
        NowMonthAnalysisCache after = NowMonthAnalysisCache.read(redisUtils.getData(key));
        CalorieAnalysisDTO afterCalorieAnalysisDTO =
                after.calorieAnalysisDTOS().stream()
                        .filter(dto -> dto.date().equals(testDate))
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "No CalorieAnalysisDTO found for requestDate: "
                                                        + testDate));

        assertAll(
                () -> assertEquals(afterCalorieAnalysisDTO.date(), testDate),
                () -> assertTrue(afterCalorieAnalysisDTO.isVisited()),
                () -> assertTrue(afterCalorieAnalysisDTO.isCalorieAchieved()));
    }
}
