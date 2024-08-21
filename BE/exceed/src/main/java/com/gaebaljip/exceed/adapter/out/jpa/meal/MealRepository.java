package com.gaebaljip.exceed.adapter.out.jpa.meal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.annotation.Timer;

public interface MealRepository extends JpaRepository<MealEntity, Long> {

    @Query(
            "select m.id from MealEntity m where m.memberEntity.id = :memberId and m.createdDate >= :today and m.createdDate < :tomorrow")
    List<Long> findMealIdsByMemberAndDaily(
            LocalDateTime today, LocalDateTime tomorrow, Long memberId);

    @Query(
            "select m from MealEntity m where m.memberEntity.id = :memberId and m.createdDate >= :today and m.createdDate < :tomorrow")
    List<MealEntity> findMealsByMemberAndDaily(
            LocalDateTime today, LocalDateTime tomorrow, Long memberId);

    @Timer
    @Query(
            "select m.id from MealEntity m where m.memberEntity.id = :memberId and m.createdDate >= :startOfMonth and m.createdDate < :endOfMonth")
    List<Long> findMealIdsByMemberAndMonth(
            LocalDateTime startOfMonth, LocalDateTime endOfMonth, Long memberId);

    void deleteByMemberEntity(MemberEntity memberEntity);

    List<MealEntity> findByMemberEntity(MemberEntity memberEntity);

    @Query("delete from MealEntity m where m.id in :ids")
    @Modifying
    @Transactional
    void deleteByAllByIdInQuery(List<Long> ids);
}
