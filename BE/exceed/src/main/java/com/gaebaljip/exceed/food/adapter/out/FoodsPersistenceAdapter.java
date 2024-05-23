package com.gaebaljip.exceed.food.adapter.out;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.dto.response.PageableFood;
import com.gaebaljip.exceed.food.application.port.out.FoodPort;
import com.gaebaljip.exceed.food.domain.Food;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FoodsPersistenceAdapter implements FoodPort {

    private final FoodRepository foodRepository;
    private final QueryDslFoodRepository queryDslFoodRepository;
    private final FoodConverter foodConverter;

    @Override
    public List<Food> query(Long memberId, LocalDate date) {
        return null;
    }

    @Override
    public List<FoodEntity> query(List<Long> foodIds) {
        return foodRepository.findAllById(foodIds);
    }

    @Override
    public Slice<Food> query(String lastFoodName, int size, String keyword) {
        PageableFood pageableFood =
                queryDslFoodRepository.findPageableFood(lastFoodName, size, keyword);
        PageRequest pageRequest = PageRequest.of(0, pageableFood.size());
        List<Food> foods = foodConverter.toModels(pageableFood.foodEntities());
        return new SliceImpl<>(foods, pageRequest, pageableFood.hasNext());
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
    public void deleteByAllByIdInQuery(List<Long> ids) {
        foodRepository.deleteByAllByIdInQuery(ids);
    }

    @Override
    public List<FoodEntity> findByMemberEntity(MemberEntity memberEntity) {
        return foodRepository.findByMemberEntity(memberEntity);
    }
}
