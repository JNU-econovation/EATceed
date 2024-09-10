package com.gaebaljip.exceed.application.port.out.food;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.food.FoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

@Component
public interface FoodPort {

    List<FoodEntity> queryAllEntities(List<Long> foodIds);

    FoodEntity command(FoodEntity foodEntity);

    void saveAll(List<FoodEntity> foodEntities);

    List<FoodEntity> findByMemberId(Long memberId);

    void deleteByAllByIdInQuery(List<Long> ids);

    List<FoodEntity> findByMemberEntity(MemberEntity memberEntity);

    FoodEntity query(Long foodId);

    void deleteById(Long foodId);
}
