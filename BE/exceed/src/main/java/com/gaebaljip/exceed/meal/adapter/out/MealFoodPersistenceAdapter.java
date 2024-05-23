package com.gaebaljip.exceed.meal.adapter.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.meal.application.port.out.MealFoodPort;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MealFoodPersistenceAdapter implements MealFoodPort {

    private final MealFoodRepository mealFoodRepository;

    @Override
    public List<MealFoodEntity> command(List<MealFoodEntity> mealFoodEntities) {
        return mealFoodRepository.saveAll(mealFoodEntities);
    }

    @Override
    public void deleteByAllByIdInQuery(List<Long> ids) {
        mealFoodRepository.deleteByAllByIdInQuery(ids);
    }

    @Override
    public List<MealFoodEntity> findByMemberEntity(MemberEntity memberEntity) {
        return mealFoodRepository.findByMemberEntity(memberEntity);
    }
}
