package com.gaebaljip.exceed.meal.application.port.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.meal.adapter.out.MealFoodEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

@Component
public interface MealFoodPort {
    List<MealFoodEntity> command(List<MealFoodEntity> mealFoodEntities);

    void deleteByAllByIdInQuery(List<Long> ids);

    List<MealFoodEntity> findByMemberEntity(MemberEntity memberEntity);
}
