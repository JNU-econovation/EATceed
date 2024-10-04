package com.gaebaljip.exceed.adapter.out.scheduler.quartz;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.out.redis.RedisUtils;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDailyAnalysisCommand;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetDailyAnalysisUsecase;
import com.gaebaljip.exceed.common.RedisScanPattern;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateCacheScheduler {

    private final RedisUtils redisUtils;
    private final DailyAnalysisCacheUpdater dailyAnalysisCacheUpdater;
    private final GetDailyAnalysisUsecase getDailyAnalysisUsecase;

    /**
     * 스케줄링 작업으로, 매일 실행되어 해당 년도와 월이 같은 캐시 키들을 조회합니다. 캐시 키의 형태는 "analysis::memberId_yyyy-MM-dd"이며,
     * 예시로 "analysis::1_2024-07-22"와 같은 형식을 가집니다.
     *
     * <p>1. 현재 날짜와 동일한 년도와 월을 가진 모든 캐시 키를 조회합니다. 2. 조회된 캐시 키들에 대해, 해당 키의 value에 어제 날짜의 분석 결과를
     * 반영합니다. 3. 분석 결과는 `cacheKeyProcessor.processKey(key)` 메서드를 통해 처리됩니다.
     *
     * <p>예를 들어, 2024년 7월 22일에 스케줄러가 실행되면 "analysis::*_2024-07-*" 패턴을 가진 모든 캐시 키들을 조회한 후, 해당 캐시에 7월
     * 21일(어제)의 분석 결과를 반영합니다.
     */
    @Scheduled(cron = "${scheduler.nutrition.cache.update}", zone = "Asia/Seoul")
    public void run() {
        Set<String> keys = scanKeys(RedisScanPattern.getAnalysisPattern(LocalDate.now()));
        for (String key : keys) {
            CalorieAnalysisDTO calorieAnalysisDTO =
                    getDailyAnalysisUsecase.executeToCalorie(
                            GetDailyAnalysisCommand.of(
                                    extractMemberId(key), getYesterdayDate().atStartOfDay()));
            dailyAnalysisCacheUpdater.updateCacheForKey(key, calorieAnalysisDTO);
        }
    }

    private Set<String> scanKeys(String pattern) {
        return redisUtils.scanKeys(pattern, 10);
    }

    /**
     * Redis Key에서 memberId를 추출하는 메서드
     *
     * @param key
     * @return memberId
     */
    private Long extractMemberId(String key) {
        // 키 형식 *analysis::memberId_yyyy-MM-dd
        String[] parts = key.split("::")[1].split("_");
        return Long.valueOf(parts[0]);
    }

    /**
     * Redis Key에서 어제 날짜를 추출하는 메서드
     *
     * @return 어제 날짜
     */
    private LocalDate getYesterdayDate() {
        return LocalDate.now().minusDays(1); // 어제 날짜 반환
    }
}
