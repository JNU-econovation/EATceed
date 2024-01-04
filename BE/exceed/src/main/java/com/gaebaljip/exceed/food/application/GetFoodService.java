package com.gaebaljip.exceed.food.application;

import com.gaebaljip.exceed.dto.response.GetFood;
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
    public GetFood execute(Long foodId) {
        FoodModel foodModel = loadFoodPort.query(foodId);
        return GetFood.builder()
                .id(foodModel.getId())
                .name(foodModel.getName())
                .build();
    }

    @Override
    public Slice<GetPageableFood> execute(String lastFoodName, int size) {
        Slice<FoodModel> foodModels = loadFoodPort.query(lastFoodName, size);
        List<GetPageableFood> pageableFoods = foodModels.getContent().stream().map(foodModel -> new GetPageableFood(foodModel.getId(), foodModel.getName())).collect(Collectors.toList());
        return new SliceImpl<>(pageableFoods, foodModels.getPageable(), foodModels.hasNext());
    }
}
