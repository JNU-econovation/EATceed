package com.gaebaljip.exceed.meal.adapter.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

public interface MealFoodRepository extends JpaRepository<MealFoodEntity, Long> {

    @Modifying
    @Transactional
    @Query("delete from MealFoodEntity mf where mf.id in :ids")
    void deleteByAllByIdInQuery(List<Long> ids);

    @Query("select mf from MealFoodEntity mf where mf.mealEntity.memberEntity = :memberEntity")
    List<MealFoodEntity> findByMemberEntity(MemberEntity memberEntity);
}
