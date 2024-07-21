package com.gaebaljip.exceed.application.service.meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.port.in.meal.GetSpecificMealQuery;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.application.port.out.meal.PresignedUrlPort;
import com.gaebaljip.exceed.common.dto.CurrentMealDTO;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;
import com.gaebaljip.exceed.common.dto.FoodDTO;
import com.gaebaljip.exceed.common.dto.MealRecordDTO;
import com.gaebaljip.exceed.common.dto.SpecificMealDTO;

import lombok.RequiredArgsConstructor;

/**
 * 특정 날짜의 식사 정보 조회
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class GetSpecificMealService implements GetSpecificMealQuery {

    private final DailyMealPort dailyMealPort;
    private final PresignedUrlPort presignedUrlPort;

    /**
     * DailyMeal 도메인이 특정 날짜 식사들을 분석하여 칼로리,단,탄,지 정보 및 특정 날짜의 식사 정보들을 반환
     *
     * @param memberId
     * @param date
     * @return SpecificMeal : 특정 날짜의 칼로리,단,탄,지 정보와 식사 정보
     */
    @Override
    @Transactional(readOnly = true)
    public SpecificMealDTO execute(Long memberId, LocalDateTime date) {
        List<Meal> meals = dailyMealPort.query(new DailyMealDTO(memberId, date));
        DailyMeal dailyMeal = new DailyMeal(meals);
        List<MealRecordDTO> mealRecordDTOS =
                meals.stream().map(meal -> createMealRecord(meal, memberId)).toList();
        CurrentMealDTO currentMealDTO =
                CurrentMealDTO.of(
                        dailyMeal.calculateCurrentCalorie(),
                        dailyMeal.calculateCurrentCarbohydrate(),
                        dailyMeal.calculateCurrentProtein(),
                        dailyMeal.calculateCurrentFat());
        return SpecificMealDTO.of(currentMealDTO, mealRecordDTOS);
    }

    private MealRecordDTO createMealRecord(Meal meal, Long memberId) {
        return MealRecordDTO.builder()
                .mealType(meal.getMealType())
                .time(meal.getMealDateTime().toLocalTime())
                .imageUri(presignedUrlPort.query(memberId, meal.getId()))
                .foodDTOS(
                        meal.getConsumedFoods().stream()
                                .map(
                                        consumedFood ->
                                                FoodDTO.builder()
                                                        .id(consumedFood.getFood().getId())
                                                        .name(consumedFood.getFood().getName())
                                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }
}
