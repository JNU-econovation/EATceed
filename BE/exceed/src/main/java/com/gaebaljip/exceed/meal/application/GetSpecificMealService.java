package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.request.DailyMeal;
import com.gaebaljip.exceed.dto.response.CurrentMeal;
import com.gaebaljip.exceed.dto.response.GetFood;
import com.gaebaljip.exceed.dto.response.SpecificMeal;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.meal.application.port.out.PresignedUrlPort;
import com.gaebaljip.exceed.meal.application.port.out.DailyMealPort;
import com.gaebaljip.exceed.meal.domain.Meal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 특정 날짜의 식사 정보 조회
 */
@Service
@RequiredArgsConstructor
public class GetSpecificMealService implements GetSpecificMealQuery {

    private final DailyMealPort dailyMealPort;
    private final PresignedUrlPort presignedUrlPort;
    public static final double ZERO = 0.0;

    @Override
    @Transactional(readOnly = true)
    public SpecificMeal execute(Long memberId, LocalDate date) {
        List<Meal> meals = dailyMealPort.query(new DailyMeal(memberId,date));
        List<com.gaebaljip.exceed.dto.response.DailyMeal> dailyMeals = new ArrayList<>();
        if(meals.isEmpty()){
            SpecificMeal specificMeal = SpecificMeal.builder()
                    .dailyMeals(dailyMeals)
                    .currentMeal(CurrentMeal.builder()
                            .protein(ZERO)
                            .fat(ZERO)
                            .carbohydrate(ZERO)
                            .calorie(ZERO)
                            .build()
                    ).build();
            return specificMeal;
        }
        com.gaebaljip.exceed.meal.domain.DailyMeal dailyMeal = new com.gaebaljip.exceed.meal.domain.DailyMeal(meals);
        setDailyMeals(meals, dailyMeals, memberId);
        return getSpecificMeal(dailyMeal, dailyMeals);
    }

    private void setDailyMeals(List<Meal> meals, List<com.gaebaljip.exceed.dto.response.DailyMeal> dailyMeals, Long memberId) {
        IntStream.range(0, meals.size()).forEach(i -> {
            com.gaebaljip.exceed.dto.response.DailyMeal dailyMeal = com.gaebaljip.exceed.dto.response.DailyMeal.builder()
                    .mealType(meals.get(i).getMealType())
                    .time(meals.get(i).getMealDateTime().toLocalTime())
                    .imageUri(presignedUrlPort.query(memberId, meals.get(i).getId()))
                    .foods(meals.get(i).getFoods().stream().map(foodModel -> GetFood.builder()
                            .id(foodModel.getId())
                            .name(foodModel.getName())
                            .build()).toList()
                    ).build();
            dailyMeals.add(dailyMeal);
        });
    }

    private SpecificMeal getSpecificMeal(com.gaebaljip.exceed.meal.domain.DailyMeal dailyMeal, List<com.gaebaljip.exceed.dto.response.DailyMeal> dailyMeals) {
        return SpecificMeal.builder()
                .currentMeal(getCurrentMeal(dailyMeal))
                .dailyMeals(dailyMeals)
                .build();
    }

    private CurrentMeal getCurrentMeal(com.gaebaljip.exceed.meal.domain.DailyMeal dailyMeal) {
        return CurrentMeal.builder()
                .calorie(dailyMeal.calculateCurrentCalorie())
                .carbohydrate(dailyMeal.calculateCurrentCarbohydrate())
                .fat(dailyMeal.calculateCurrentFat())
                .protein(dailyMeal.calculateCurrentProtein())
                .build();
    }
}
