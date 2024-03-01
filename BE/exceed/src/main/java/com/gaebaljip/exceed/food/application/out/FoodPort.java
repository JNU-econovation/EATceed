package com.gaebaljip.exceed.food.application.out;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.domain.Food;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface FoodPort {

    List<Food> query(Long memberId, LocalDate date);

    List<FoodEntity> query(List<Long> foodIds);

    Slice<Food> query(String lastFoodName, int size, String keyword);

}
