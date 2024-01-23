package com.gaebaljip.exceed.food.application;

import com.gaebaljip.exceed.dto.response.GetPageableFood;
import com.gaebaljip.exceed.food.application.out.LoadFoodPort;
import com.gaebaljip.exceed.food.application.in.GetFoodQuery;
import com.gaebaljip.exceed.food.domain.FoodModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetFoodService implements GetFoodQuery {

    private final LoadFoodPort loadFoodPort;
    @Override
    public Slice<GetPageableFood> execute(String lastFoodName, String keyword, int size) {
        Slice<FoodModel> foodModels = loadFoodPort.query(lastFoodName, size, keyword);
        List<GetPageableFood> pageableFoods = foodModels.getContent().stream().map(foodModel -> new GetPageableFood(foodModel.getId(), foodModel.getName())).collect(Collectors.toList());
        return new SliceImpl<>(pageableFoods, foodModels.getPageable(), foodModels.hasNext());
    }
}
