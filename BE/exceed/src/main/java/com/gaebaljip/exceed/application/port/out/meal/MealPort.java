package com.gaebaljip.exceed.application.port.out.meal;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

@Component
public interface MealPort {
    MealEntity command(MealEntity mealEntity);

    List<MealEntity> findByMemberEntity(MemberEntity memberEntity);

    void deleteByAllByIdInQuery(List<Long> ids);
}
