package com.gaebaljip.exceed.meal.adapter.out;

import com.gaebaljip.exceed.achieve.domain.DailyRecord;
import com.gaebaljip.exceed.food.adapter.out.FoodConverter;
import com.gaebaljip.exceed.meal.domain.MealModel;
import com.gaebaljip.exceed.meal.domain.MealsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MealConverter {

    private final FoodConverter foodConverter;

    public MealModel toModel(MealEntity mealEntity) {
        return MealModel.builder()
                .mealType(mealEntity.getMealType())
                .mealDateTime(mealEntity.getCreatedDate().toLocalDateTime())
                .foodModels(mealEntity.getMealFoodEntity().stream()
                        .map(mealFoodEntity -> foodConverter.toModel(mealFoodEntity.getFoodEntity()))
                        .toList())
                .build();
    }

    public List<MealModel> toModels(List<MealEntity> mealEntities) {
        return mealEntities.stream()
                .map(this::toModel)
                .toList();
    }


    public DailyRecord toDailyAchieve(MealEntity mealEntity) {
        MealModel mealModel = toModel(mealEntity);
        DailyRecord dailyRecord = DailyRecord.builder()
                .currentCalorie(mealModel.getCurrentCalorie())
                .currentFat(mealModel.getCurrentFat())
                .currentCarbohydrate(mealModel.getCurrentCarbohydrate())
                .currentProtein(mealModel.getCurrentProtein())
                .date(mealEntity.getCreatedDate().toLocalDateTime().toLocalDate())
                .build();
        return dailyRecord;
    }

    public List<DailyRecord> toDailyAchieves(List<MealEntity> monthMealEntities) {
        // MealEntity를 날짜별로 그룹화
        Map<LocalDate, List<MealEntity>> groupedMeal = monthMealEntities.stream()
                .collect(Collectors.groupingBy(mealEntity -> mealEntity.getCreatedDate().toLocalDateTime().toLocalDate()));

        // 각 그룹을 DailyAchieve 객체로 변환
        List<DailyRecord> dailyRecords = groupedMeal.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<MealModel> mealModels = toModels(entry.getValue());
                    MealsModel mealsModel = new MealsModel(mealModels);
                    DailyRecord dailyRecord = DailyRecord.builder()
                            .currentCalorie(mealsModel.calculateCurrentCalorie())
                            .currentProtein(mealsModel.calculateCurrentProtein())
                            .currentCarbohydrate(mealsModel.calculateCurrentCarbohydrate())
                            .currentFat(mealsModel.calculateCurrentFat())
                            .date(date)
                            .build();
                    return dailyRecord;
                }).collect(Collectors.toList());
        return dailyRecords;
    }

}
