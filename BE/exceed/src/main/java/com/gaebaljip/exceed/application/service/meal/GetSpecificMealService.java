package com.gaebaljip.exceed.application.service.meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.meal.MealEntity;
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
        List<MealEntity> mealEntities = dailyMealPort.queryMeals(new DailyMealDTO(memberId, date));
        List<MealRecordDTO> mealRecordDTOS =
                mealEntities.stream().map(meal -> createMealRecord(meal, memberId)).toList();
        DailyMealFoods dailyMealFoods =
                dailyMealPort.queryMealFoods(
                        mealEntities.stream().map(meal -> meal.getId()).toList());
        CurrentMealDTO currentMealDTO =
                CurrentMealDTO.of(
                        dailyMealFoods.calculateCurrentCalorie(),
                        dailyMealFoods.calculateCurrentCarbohydrate(),
                        dailyMealFoods.calculateCurrentProtein(),
                        dailyMealFoods.calculateCurrentFat());
        return SpecificMealDTO.of(currentMealDTO, mealRecordDTOS);
    }

    private MealRecordDTO createMealRecord(MealEntity mealEntity, Long memberId) {
        return MealRecordDTO.builder()
                .mealType(mealEntity.getMealType())
                .time(mealEntity.getCreatedDate().toLocalTime())
                .imageUri(presignedUrlPort.query(memberId, mealEntity.getId()))
                .foodDTOS(
                        mealEntity.getMealFoodEntities().stream()
                                .map(
                                        mealFood ->
                                                FoodDTO.builder()
                                                        .id(mealFood.getFoodEntity().getId())
                                                        .name(mealFood.getFoodEntity().getName())
                                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }
}
