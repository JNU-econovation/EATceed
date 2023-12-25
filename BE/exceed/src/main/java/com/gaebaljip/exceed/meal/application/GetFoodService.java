package com.gaebaljip.exceed.meal.application;

import com.gaebaljip.exceed.dto.response.GetFood;
import com.gaebaljip.exceed.food.application.out.LoadFoodPort;
import com.gaebaljip.exceed.food.domain.FoodModel;
import com.gaebaljip.exceed.meal.application.port.in.GetFoodQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GetFoodService implements GetFoodQuery {

    private final LoadFoodPort loadFoodPort;

    @Override
    public GetFood execute(Long memberId, LocalDate date) {
        loadFoodPort.query(memberId, date);
        FoodModel foodModel = FoodModel.builder()
                .name("쭈꾸미")
                .build();
        return GetFood.builder()
                .id(foodModel.getId())
                .name(foodModel.getName())
                .build();
    }
}
