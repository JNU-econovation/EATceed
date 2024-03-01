package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.application.out.FoodPort;
import com.gaebaljip.exceed.meal.adapter.out.MealEntity;
import com.gaebaljip.exceed.meal.adapter.out.MealFoodEntity;
import com.gaebaljip.exceed.meal.application.port.in.EatMealCommand;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.out.MealFoodPort;
import com.gaebaljip.exceed.meal.application.port.out.MealPort;
import com.gaebaljip.exceed.meal.exception.InvalidMultipleException;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EatMealService implements EatMealUsecase {

    private final FoodPort foodPort;
    private final MemberPort memberPort;
    private final MealPort mealPort;
    private final MealFoodPort mealFoodPort;

    @Override
    @Transactional
    public Long execute(EatMealCommand command) {
        validateMultiple(command.multiple());
        List<FoodEntity> foodEntities = foodPort.query(command.foodIds());
        MemberEntity memberEntity = memberPort.query(command.memberId());
        MealEntity mealEntity = mealPort.command(MealEntity.createMeal(memberEntity, command.multiple(), command.mealType()));
        mealFoodPort.command(MealFoodEntity.createMealFoods(foodEntities, mealEntity));
        return mealEntity.getId();
    }

    private void validateMultiple(Double multiple) {
        if (multiple <= 0 || multiple > 100) {
            throw new InvalidMultipleException();
        }
    }
}
