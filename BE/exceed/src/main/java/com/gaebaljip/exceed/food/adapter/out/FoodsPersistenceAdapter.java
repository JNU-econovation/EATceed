package com.gaebaljip.exceed.food.adapter.out;

import com.gaebaljip.exceed.food.application.out.LoadFoodsSpecificDate;
import com.gaebaljip.exceed.food.domain.FoodModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FoodsPersistenceAdapter implements LoadFoodsSpecificDate {

    @Override
    public List<FoodModel> query(Long memberId, LocalDate date) {
        return null;
    }
}
