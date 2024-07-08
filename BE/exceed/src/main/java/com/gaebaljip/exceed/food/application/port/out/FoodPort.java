package com.gaebaljip.exceed.food.application.port.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

@Component
public interface FoodPort {

    List<FoodEntity> queryAllEntities(List<Long> foodIds);

    FoodEntity command(FoodEntity foodEntity);

    void saveAll(List<FoodEntity> foodEntities);

    List<FoodEntity> findByMemberId(Long memberId);

    void deleteByAllByIdInQuery(List<Long> ids);

    List<FoodEntity> findByMemberEntity(MemberEntity memberEntity);

    FoodEntity query(Long foodId);
}
