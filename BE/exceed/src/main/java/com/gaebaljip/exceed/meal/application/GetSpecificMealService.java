package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.request.TodayMeal;
import com.gaebaljip.exceed.dto.response.CurrentMeal;
import com.gaebaljip.exceed.dto.response.Food;
import com.gaebaljip.exceed.dto.response.MealRecord;
import com.gaebaljip.exceed.dto.response.SpecificMeal;
import com.gaebaljip.exceed.meal.application.port.in.GetSpecificMealQuery;
import com.gaebaljip.exceed.meal.application.port.out.PresignedUrlPort;
import com.gaebaljip.exceed.meal.application.port.out.DailyMealPort;
import com.gaebaljip.exceed.meal.domain.DailyMeal;
import com.gaebaljip.exceed.meal.domain.Meal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Meal> meals = dailyMealPort.query(new TodayMeal(memberId,date));

        if(meals.isEmpty()) {
            return createEmptySpecificMeal();
        }
        DailyMeal dailyMeal = new DailyMeal(meals);

        List<MealRecord> mealRecords = meals.stream()
                .map(meal -> createMealRecord(meal, memberId))
                .toList();

        return SpecificMeal.builder()
                .currentMeal(getCurrentMeal(dailyMeal))
                .mealRecords(mealRecords)
                .build();
    }

    private SpecificMeal createEmptySpecificMeal() {
        return SpecificMeal.builder()
                .mealRecords(List.of())
                .currentMeal(CurrentMeal.builder()
                        .protein(ZERO)
                        .fat(ZERO)
                        .carbohydrate(ZERO)
                        .calorie(ZERO)
                        .build())
                .build();
    }

    private MealRecord createMealRecord(Meal meal, Long memberId) {
        return MealRecord.builder()
                .mealType(meal.getMealType())
                .time(meal.getMealDateTime().toLocalTime())
                .imageUri(presignedUrlPort.query(memberId, meal.getId()))
                .foods(meal.getFoods().stream()
                        .map(foodModel -> Food.builder()
                                .id(foodModel.getId())
                                .name(foodModel.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private CurrentMeal getCurrentMeal(DailyMeal dailyMeal) {
        return CurrentMeal.builder()
                .calorie(dailyMeal.calculateCurrentCalorie())
                .carbohydrate(dailyMeal.calculateCurrentCarbohydrate())
                .fat(dailyMeal.calculateCurrentFat())
                .protein(dailyMeal.calculateCurrentProtein())
                .build();
    }
}
