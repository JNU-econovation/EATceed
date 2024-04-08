package com.gaebaljip.exceed.meal.application;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

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

/**
 * 식사를 등록한다.
 *
 * @author hwangdaesun
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class EatMealService implements EatMealUsecase {

    private final FoodPort foodPort;
    private final MemberPort memberPort;
    private final MealPort mealPort;
    private final MealFoodPort mealFoodPort;

    /**
     * 몇 인분(multiple)을 검증하고, 식사를 등록한다.
     *
     * @param command : 누가 무엇을 언제 얼마나 먹었는 지에 대한 정보가 들어있다.
     * @return mealId : 식사 엔티티의 PK
     * @throws InvalidMultipleException : 0인분 이하거나 100인분 초과일 경우
     */
    @Override
    @Transactional
    public Long execute(EatMealCommand command) {
        validateMultiple(command.multiple());
        List<FoodEntity> foodEntities = foodPort.query(command.foodIds());
        MemberEntity memberEntity = memberPort.query(command.memberId());
        MealEntity mealEntity =
                mealPort.command(
                        MealEntity.createMeal(
                                memberEntity, command.multiple(), command.mealType()));
        mealFoodPort.command(MealFoodEntity.createMealFoods(foodEntities, mealEntity));
        return mealEntity.getId();
    }

    private void validateMultiple(Double multiple) {
        if (multiple <= 0 || multiple > 100) {
            throw InvalidMultipleException.EXECPTION;
        }
    }
}
