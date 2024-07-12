package com.gaebaljip.exceed.application.service.meal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.port.in.meal.GetCurrentMealQuery;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleException;
import com.gaebaljip.exceed.dto.CurrentMealDTO;
import com.gaebaljip.exceed.dto.TodayMealDTO;

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

    public static final double ZERO = 0.0;
    private final DailyMealPort dailyMealPort;

    /**
     * DailyMeal 도메인이 오늘 먹은 식사들을 분석하여 칼로리,단,탄,지 정보를 반환한다. 만약 식사 정보가 존재하지 않을 경우는 칼로리,단,탄,지 모두 ZERO를
     * 반환한다.
     *
     * @param memberId : 누가 무엇을 언제 얼마나 먹었는 지에 대한 정보가 들어있다.
     * @return mealId : 식사 엔티티의 PK
     * @throws InvalidMultipleException : 0인분 이하거나 100인분 초과일 경우
     */
    @Override
    @Transactional(readOnly = true)
    public CurrentMealDTO execute(Long memberId) {
        List<Meal> meals = dailyMealPort.query(new TodayMealDTO(memberId, LocalDateTime.now()));
        if (meals.isEmpty()) {
            return CurrentMealDTO.builder()
                    .calorie(ZERO)
                    .carbohydrate(ZERO)
                    .fat(ZERO)
                    .protein(ZERO)
                    .build();
        } else {
            DailyMeal dailyMeal = new DailyMeal(meals);
            return CurrentMealDTO.builder()
                    .calorie(dailyMeal.calculateCurrentCalorie())
                    .carbohydrate(dailyMeal.calculateCurrentCarbohydrate())
                    .fat(dailyMeal.calculateCurrentFat())
                    .protein(dailyMeal.calculateCurrentProtein())
                    .build();
        }
    }
}
