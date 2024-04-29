package com.gaebaljip.exceed.meal.adapter.out;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MealRepository extends JpaRepository<MealEntity, Long> {

    @Query(
            "select m from MealEntity m join fetch m.mealFoodEntity mf join fetch mf.foodEntity where m.createdDate >= :today and m.createdDate < :tomorrow and m.memberEntity.id = :memberId")
    List<MealEntity> findAllTodayMeal(LocalDateTime today, LocalDateTime tomorrow, Long memberId);

    @Query(
            "select m from MealEntity m join fetch m.mealFoodEntity mf join fetch mf.foodEntity where m.createdDate >= :startOfMonth and m.createdDate < :endOfMonth and m.memberEntity.id = :memberId")
    List<MealEntity> findMealsByMemberAndMonth(
            LocalDateTime startOfMonth, LocalDateTime endOfMonth, Long memberId);
}
