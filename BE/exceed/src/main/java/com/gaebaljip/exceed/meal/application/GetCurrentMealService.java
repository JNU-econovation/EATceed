package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.CurrentMeal;
import com.gaebaljip.exceed.food.domain.FoodModel;
import com.gaebaljip.exceed.meal.domain.MealModel;
import com.gaebaljip.exceed.meal.application.port.out.LoadMealPort;
import com.gaebaljip.exceed.meal.application.port.in.GetCurrentMealQuery;
import com.gaebaljip.exceed.meal.domain.MealType;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.MemberModel;
import com.gaebaljip.exceed.member.domain.PhysiqueModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCurrentMealService implements GetCurrentMealQuery {

    private final LoadMealPort loadMealPort;

    @Override
    public CurrentMeal execute(Long memberId, LocalDate date) {
        loadMealPort.query(memberId, date);
        PhysiqueModel physiqueModel = PhysiqueModel.builder()
                .gender(true)
                .age(25)
                .activity(Activity.NOT_ACTIVE)
                .height(171.2)
                .weight(60.2)
                .build();
        MemberModel memberModel = MemberModel.builder()
                .physiqueModel(physiqueModel)
                .etc("뭐든 다 잘먹음")
                .build();

        FoodModel foodModel = FoodModel.builder()
                .name("밥")
                .calorie(400)
                .carbohydrate(300)
                .protein(800)
                .fat(20)
                .build();

        FoodModel foodModel1 = FoodModel.builder()
                .name("된장찌개")
                .calorie(1000)
                .carbohydrate(600)
                .protein(300)
                .fat(100)
                .build();

        MealModel mealModel = MealModel.builder()
                .memberModel(memberModel)
                .mealType(MealType.LUNCH)
                .foodModels(List.of(foodModel, foodModel1))
                .build();

        return CurrentMeal.builder()
                .currentCalorie(mealModel.getCurentCalorie())
                .currentCarbohydrate(mealModel.getCurrentCarbohydrate())
                .currentProtein(mealModel.getCurrentProtein())
                .currentFat(mealModel.getCurrentFat())
                .build();
    }
}
