package com.gaebaljip.exceed.meal.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.dto.request.TodayMealDTO;
import com.gaebaljip.exceed.dto.response.CurrentMealDTO;
import com.gaebaljip.exceed.dto.response.FoodDTO;
import com.gaebaljip.exceed.dto.response.MealRecordDTO;
import com.gaebaljip.exceed.dto.response.SpecificMealDTO;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.meal.application.port.out.DailyMealPort;
import com.gaebaljip.exceed.meal.application.port.out.PresignedUrlPort;
import com.gaebaljip.exceed.meal.domain.DailyMeal;
import com.gaebaljip.exceed.meal.domain.Meal;

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
    public static final double ZERO = 0.0;

    /**
     * DailyMeal 도메인이 특정 날짜 식사들을 분석하여 칼로리,단,탄,지 정보를 반환한다. 만약 식사 정보가 존재하지 않을 경우는 칼로리,단,탄,지 모두 ZERO를
     * 반환한다. 또한, 특정 날짜의 식사 정보들을 반환한다.
     *
     * @param memberId
     * @param date
     * @return SpecificMeal : 특정 날짜의 칼로리,단,탄,지 정보와 식사 정보
     */
    @Override
    @Transactional(readOnly = true)
    public SpecificMealDTO execute(Long memberId, LocalDateTime date) {
        List<Meal> meals = dailyMealPort.query(new TodayMealDTO(memberId, date));

        if (meals.isEmpty()) {
            return createEmptySpecificMeal();
        }
        DailyMeal dailyMeal = new DailyMeal(meals);

        List<MealRecordDTO> mealRecordDTOS =
                meals.stream().map(meal -> createMealRecord(meal, memberId)).toList();

        return SpecificMealDTO.builder()
                .currentMeal(getCurrentMeal(dailyMeal))
                .mealRecords(mealRecordDTOS)
                .build();
    }

    private SpecificMealDTO createEmptySpecificMeal() {
        return SpecificMealDTO.builder()
                .mealRecords(List.of())
                .currentMeal(
                        CurrentMealDTO.builder()
                                .protein(ZERO)
                                .fat(ZERO)
                                .carbohydrate(ZERO)
                                .calorie(ZERO)
                                .build())
                .build();
    }

    private MealRecordDTO createMealRecord(Meal meal, Long memberId) {
        return MealRecordDTO.builder()
                .mealType(meal.getMealType())
                .time(meal.getMealDateTime().toLocalTime())
                .imageUri(presignedUrlPort.query(memberId, meal.getId()))
                .foods(
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

    private CurrentMealDTO getCurrentMeal(DailyMeal dailyMeal) {
        return CurrentMealDTO.builder()
                .calorie(dailyMeal.calculateCurrentCalorie())
                .carbohydrate(dailyMeal.calculateCurrentCarbohydrate())
                .fat(dailyMeal.calculateCurrentFat())
                .protein(dailyMeal.calculateCurrentProtein())
                .build();
    }
}
