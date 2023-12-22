package com.gaebaljip.exceed.meal.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface MealRepository extends JpaRepository<MealEntity, Long> {

    @Query("select m from MealEntity m where m.createdDate >= :today and m.createdDate < :tomorrow and m.memberEntity.id = :memberId")
    List<MealEntity> findAllTodayMeal(Timestamp today, Timestamp tomorrow, Long memberId);
}
