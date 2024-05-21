package com.gaebaljip.exceed.meal.application.port.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.meal.adapter.out.MealEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

@Component
public interface MealPort {
    MealEntity command(MealEntity mealEntity);

    List<MealEntity> findByMemberEntity(MemberEntity memberEntity);

    void deleteByAllByIdInQuery(List<Long> ids);
}
