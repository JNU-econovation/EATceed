package com.gaebaljip.exceed.application.service.meal;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.domain.food.FoodEntity;
import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.meal.EatMealCommand;
import com.gaebaljip.exceed.application.port.in.meal.EatMealUsecase;
import com.gaebaljip.exceed.application.port.out.food.FoodPort;
import com.gaebaljip.exceed.application.port.out.meal.MealFoodPort;
import com.gaebaljip.exceed.application.port.out.meal.MealPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleException;

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
     * 몇 인분(multiple) 검증 식사를 등록한다.
     *
     * @param command : 누가 무엇을 언제 얼마나 먹었는 지에 대한 정보가 들어있다.
     * @return mealId : 식사 엔티티의 PK
     * @throws InvalidMultipleException : 0인분 이하거나 100인분 초과일 경우
     */
    @Override
    @Transactional
    public Long execute(EatMealCommand command) {
        List<FoodEntity> foodEntities =
                foodPort.queryAllEntities(
                        command.eatMealFoodDTOS().stream()
                                .mapToLong(eatMealFood -> eatMealFood.foodId())
                                .boxed()
                                .toList());
        MemberEntity memberEntity = memberPort.query(command.memberId());
        MealEntity mealEntity =
                mealPort.command(MealEntity.createMeal(memberEntity, command.mealType()));
        mealFoodPort.command(
                MealFoodEntity.createMealFoods(
                        foodEntities, mealEntity, command.eatMealFoodDTOS()));
        return mealEntity.getId();
    }
}
