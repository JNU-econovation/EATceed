package com.gaebaljip.exceed.food.adapter.out;

import com.gaebaljip.exceed.dto.response.PageableFood;
import com.gaebaljip.exceed.food.application.out.LoadFoodPort;
import com.gaebaljip.exceed.food.domain.FoodModel;
import com.gaebaljip.exceed.food.exception.FoodNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodsPersistenceAdapter implements LoadFoodPort {

    private final FoodRepository foodRepository;
    private final QueryDslFoodRepository queryDslFoodRepository;
    private final FoodConverter foodConverter;

    @Override
    public List<FoodModel> query(Long memberId, LocalDate date) {
        return null;
    }

    @Override
    public List<FoodEntity> query(List<Long> foodIds) {
        return foodRepository.findAllById(foodIds);
    }

    @Override
    public Slice<FoodModel> query(String lastFoodName, int size, String keyword) {
        PageableFood pageableFood = queryDslFoodRepository.findPageableFood(lastFoodName, size, keyword);
        PageRequest pageRequest = PageRequest.of(0, pageableFood.size());
        List<FoodModel> foodModels = foodConverter.toModels(pageableFood.foodEntities());
        return new SliceImpl<>(foodModels, pageRequest, pageableFood.hasNext());
    }


}
