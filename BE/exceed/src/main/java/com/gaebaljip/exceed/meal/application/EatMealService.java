package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.application.out.LoadFoodPort;
import com.gaebaljip.exceed.meal.adapter.out.MealEntity;
import com.gaebaljip.exceed.meal.adapter.out.MealFoodEntity;
import com.gaebaljip.exceed.meal.application.port.in.EatMealCommand;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.out.RecordMealFoodPort;
import com.gaebaljip.exceed.meal.application.port.out.RecordMealPort;
import com.gaebaljip.exceed.meal.exception.InvalidMultipleException;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EatMealService implements EatMealUsecase {

    private final LoadFoodPort loadFoodPort;
    private final LoadMemberPort loadMemberPort;
    private final RecordMealPort recordMealPort;
    private final RecordMealFoodPort recordMealFoodPort;

    @Override
    @Transactional
    public Long execute(EatMealCommand command) {
        validateMultiple(command.multiple());
        List<FoodEntity> foodEntities = loadFoodPort.query(command.foodIds());
        List<MealFoodEntity> mealFoodEntities = recordMealFoodPort.query(MealFoodEntity.createMealFoods(foodEntities));
        MemberEntity memberEntity = loadMemberPort.query(command.memberId());
        return recordMealPort.query(MealEntity.createMeal(memberEntity, command.multiple(), command.mealType(), mealFoodEntities));
    }

    private void validateMultiple(Double multiple) {
        if (multiple <= 0 || multiple > 100) {
            throw new InvalidMultipleException();
        }
    }
}
