package com.gaebaljip.exceed.adapter.out.jpa.food;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.food.FoodEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.out.food.FoodPort;
import com.gaebaljip.exceed.common.exception.food.FoodNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FoodsPersistenceAdapter implements FoodPort {

    private final FoodRepository foodRepository;

    @Override
    public List<FoodEntity> queryAllEntities(List<Long> foodIds) {
        return foodRepository.findAllById(foodIds);
    }

    @Override
    public FoodEntity command(FoodEntity foodEntity) {
        return foodRepository.save(foodEntity);
    }

    @Override
    public void saveAll(List<FoodEntity> foodEntities) {
        foodRepository.saveAll(foodEntities);
    }

    @Override
    public List<FoodEntity> findByMemberId(Long memberId) {
        return foodRepository.findByMemberEntityId(memberId);
    }

    @Override
    public void deleteByAllByIdInQuery(List<Long> ids) {
        foodRepository.deleteByAllByIdInQuery(ids);
    }

    @Override
    public List<FoodEntity> findByMemberEntity(MemberEntity memberEntity) {
        return foodRepository.findByMemberEntity(memberEntity);
    }

    @Override
    public FoodEntity query(Long foodId) {
        return foodRepository.findById(foodId).orElseThrow(() -> FoodNotFoundException.Exception);
    }

    @Override
    public void deleteById(Long foodId) {
        foodRepository.deleteById(foodId);
    }
}
