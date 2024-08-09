package com.gaebaljip.exceed.adapter.out.jpa.meal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

public interface MealFoodRepository extends JpaRepository<MealFoodEntity, Long> {

    @Modifying
    @Transactional
    @Query("delete from MealFoodEntity mf where mf.id in :ids")
    void deleteByAllByIdInQuery(List<Long> ids);

    @Query("select mf from MealFoodEntity mf where mf.mealEntity.memberEntity = :memberEntity")
    List<MealFoodEntity> findByMemberEntity(MemberEntity memberEntity);

    @Query(
            "select mft from MealFoodEntity mft join fetch mft.foodEntity where mft.mealEntity.id in :ids")
    List<MealFoodEntity> findMFTByIdInQuery(List<Long> ids);
}
