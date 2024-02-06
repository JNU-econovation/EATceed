package com.gaebaljip.exceed.food.application.out;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.domain.FoodModel;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface LoadFoodPort {

    List<FoodModel> query(Long memberId, LocalDate date);

    List<FoodEntity> query(List<Long> foodIds);

    Slice<FoodModel> query(String lastFoodName, int size, String keyword);

}
