package com.gaebaljip.exceed.food.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.common.EatCeedStaticMessage;
import com.gaebaljip.exceed.common.redis.RedisUtils;
import com.gaebaljip.exceed.dto.response.GetFoodResponse;
import com.gaebaljip.exceed.dto.response.GetFoodsAutoResponse;
import com.gaebaljip.exceed.dto.response.GetPageableFoodDTO;
import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.application.port.in.GetFoodQuery;
import com.gaebaljip.exceed.food.application.port.out.FoodPort;
import com.gaebaljip.exceed.food.domain.Food;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetFoodService implements GetFoodQuery {
    private final String postfix = ";";
    private final FoodPort loadFoodPort;
    private final RedisUtils redisUtils;

    @Override
    public Slice<GetPageableFoodDTO> execute(String lastFoodName, String keyword, int size) {
        Slice<Food> foodModels = loadFoodPort.query(lastFoodName, size, keyword);
        List<GetPageableFoodDTO> pageableFoods =
                foodModels.getContent().stream()
                        .map(
                                foodModel ->
                                        new GetPageableFoodDTO(foodModel.getId(), foodModel.getName()))
                        .collect(Collectors.toList());
        return new SliceImpl<>(pageableFoods, foodModels.getPageable(), foodModels.hasNext());
    }

    @Override
    @Transactional(readOnly = true)
    public GetFoodsAutoResponse execute(String prefix) {
        Set<Object> autoComplete = getAutoComplete(prefix);
        List<String> autoCompleteList =
                autoComplete.stream()
                        .filter(o -> checkPrefixAndPostfix(o, prefix, postfix))
                        .map(o -> (String) o)
                        .limit(10)
                        .toList();
        return GetFoodsAutoResponse.from(autoCompleteList);
    }

    private Set<Object> getAutoComplete(String prefix) {
        Long index = redisUtils.zRank(EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY, prefix);
        return redisUtils.zRange(EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY, index, index + 10);
    }

    private boolean checkPrefixAndPostfix(Object o, String prefix, String postfix) {
        return o instanceof String string && string.startsWith(prefix) && string.endsWith(postfix);
    }

    @Override
    public GetFoodResponse execute(Long foodId) {
        FoodEntity foodEntity = loadFoodPort.query(foodId);
        return GetFoodResponse.of(foodEntity);
    }
}
