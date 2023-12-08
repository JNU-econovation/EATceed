package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.GetFood;
import com.gaebaljip.exceed.food.application.out.LoadFoodsSpecificDate;
import com.gaebaljip.exceed.food.domain.FoodModel;
import com.gaebaljip.exceed.meal.application.port.in.GetFoodQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GetFoodService implements GetFoodQuery {

    private final LoadFoodsSpecificDate loadFoodsSpecificDate;

    @Override
    public GetFood execute(Long memberId, LocalDate date) {
        loadFoodsSpecificDate.query(memberId, date);
        FoodModel foodModel = FoodModel.builder()
                .name("쭈꾸미")
                .build();
        return new GetFood(foodModel.getName());
    }
}
