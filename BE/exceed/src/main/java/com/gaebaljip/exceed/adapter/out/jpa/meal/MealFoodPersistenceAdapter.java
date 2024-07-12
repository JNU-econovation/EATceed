package com.gaebaljip.exceed.adapter.out.jpa.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.out.meal.MealFoodPort;

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
