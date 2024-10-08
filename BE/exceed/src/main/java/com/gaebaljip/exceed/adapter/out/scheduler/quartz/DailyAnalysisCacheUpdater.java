package com.gaebaljip.exceed.adapter.out.scheduler.quartz;

import static com.gaebaljip.exceed.common.RedisKeys.NOW_ANALYSIS_CACHE_TTL;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.out.redis.RedisUtils;
import com.gaebaljip.exceed.common.dto.CalorieAnalysisDTO;
import com.gaebaljip.exceed.common.dto.NowMonthAnalysisCache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyAnalysisCacheUpdater {

    private final RedisUtils redisUtils;

    /** 특정 회원의 특정 날짜의 칼로리 달성 여부 조회 결과(CalorieAnalysisDTO)를 사용해 해당 월의 분석 결과 update */
    public void updateCacheForKey(String key, CalorieAnalysisDTO calorieAnalysisDTO) {
        try {
            log.info(
                    "DailyAnalysisCacheUpdater 실행, key : {} calorieAnalysisDTO : {}",
                    key,
                    calorieAnalysisDTO);
            String original = redisUtils.getData(key);
            NowMonthAnalysisCache overWrite =
                    NowMonthAnalysisCache.overWrite(
                            NowMonthAnalysisCache.read(original), calorieAnalysisDTO);
            log.info("original : {}, overWrite 완료 : {}", original, overWrite);
            redisUtils.setData(key, NowMonthAnalysisCache.write(overWrite), NOW_ANALYSIS_CACHE_TTL);
        } catch (Exception e) {
            log.error("Error processing key: {}, message: {}", key, e.getMessage());
        }
    }
}
