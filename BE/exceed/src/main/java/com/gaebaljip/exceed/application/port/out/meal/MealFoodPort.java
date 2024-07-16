package com.gaebaljip.exceed.application.port.out.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

@Component
public interface MealFoodPort {
    List<MealFoodEntity> command(List<MealFoodEntity> mealFoodEntities);

    void deleteByAllByIdInQuery(List<Long> ids);

    List<MealFoodEntity> findByMemberEntity(MemberEntity memberEntity);
}
