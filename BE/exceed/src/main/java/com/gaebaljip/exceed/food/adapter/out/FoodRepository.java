package com.gaebaljip.exceed.food.adapter.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    List<FoodEntity> findByMemberEntityId(Long memberId);

    @Query("delete from FoodEntity f where f.id in :ids")
    @Modifying
    @Transactional
    void deleteByAllByIdInQuery(List<Long> ids);

    List<FoodEntity> findByMemberEntity(MemberEntity memberEntity);
}
