package com.gaebaljip.exceed.adapter.out.jpa.food;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.food.FoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    List<FoodEntity> findByMemberEntityId(Long memberId);

    @Query("delete from FoodEntity f where f.id in :ids")
    @Modifying
    @Transactional
    void deleteByAllByIdInQuery(List<Long> ids);

    List<FoodEntity> findByMemberEntity(MemberEntity memberEntity);
}
