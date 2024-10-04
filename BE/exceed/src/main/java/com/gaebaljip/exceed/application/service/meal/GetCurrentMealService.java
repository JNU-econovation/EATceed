package com.gaebaljip.exceed.application.service.meal;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.port.in.meal.GetCurrentMealQuery;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.dto.CurrentMealDTO;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;
import com.gaebaljip.exceed.common.exception.meal.NotSameDateException;

import lombok.RequiredArgsConstructor;

/**
 * 오늘 먹은 식사 정보 조회
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class GetCurrentMealService implements GetCurrentMealQuery {

    private final DailyMealPort dailyMealPort;

    /**
     * DailyMeal 도메인이 오늘 먹은 식사들을 분석하여 칼로리,단,탄,지 정보를 반환
     *
     * @param memberId : 누가 무엇을 언제 얼마나 먹었는 지에 대한 정보
     * @return currentMealDTO : calorie, carbohydrate, protein, fat
     * @throws NotSameDateException : 같은 날짜가 아닐 경우
     */
    @Override
    @Transactional(readOnly = true)
    @Timer
    public CurrentMealDTO execute(Long memberId) {
        DailyMealFoods dailyMealFoods =
                dailyMealPort.queryMealFoodsForDay(new DailyMealDTO(memberId, LocalDateTime.now()));
        return CurrentMealDTO.of(
                dailyMealFoods.calculateCurrentCalorie(),
                dailyMealFoods.calculateCurrentCarbohydrate(),
                dailyMealFoods.calculateCurrentProtein(),
                dailyMealFoods.calculateCurrentFat());
    }
}
