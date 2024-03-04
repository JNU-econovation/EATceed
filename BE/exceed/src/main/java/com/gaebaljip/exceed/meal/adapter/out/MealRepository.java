package com.gaebaljip.exceed.meal.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface MealRepository extends JpaRepository<MealEntity, Long> {

    @Query("select m from MealEntity m join fetch m.mealFoodEntity mf join fetch mf.foodEntity where m.createdDate >= :today and m.createdDate < :tomorrow and m.memberEntity.id = :memberId")
    List<MealEntity> findAllTodayMeal(Timestamp today, Timestamp tomorrow, Long memberId);

    @Query("select m from MealEntity m join fetch m.mealFoodEntity mf join fetch mf.foodEntity where m.createdDate >= :startOfMonth and m.createdDate <= :endOfMonth and m.memberEntity.id = :memberId")
    List<MealEntity> findMealsByMemberAndMonth(Timestamp startOfMonth, Timestamp endOfMonth, Long memberId);
}
