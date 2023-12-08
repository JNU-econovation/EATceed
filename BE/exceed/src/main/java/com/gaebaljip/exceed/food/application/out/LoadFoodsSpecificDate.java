package com.gaebaljip.exceed.food.application.out;

import com.gaebaljip.exceed.food.domain.FoodModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface LoadFoodsSpecificDate {

    List<FoodModel> query(Long memberId, LocalDate date);

}
