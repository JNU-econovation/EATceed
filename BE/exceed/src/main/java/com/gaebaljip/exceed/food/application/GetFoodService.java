package com.gaebaljip.exceed.food.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.common.EatCeedStaticMessage;
import com.gaebaljip.exceed.dto.response.GetFoodsResponse;
import com.gaebaljip.exceed.dto.response.GetPageableFood;
import com.gaebaljip.exceed.food.application.in.GetFoodQuery;
import com.gaebaljip.exceed.food.application.out.FoodPort;
import com.gaebaljip.exceed.food.domain.Food;
import com.gaebaljip.exceed.infrastructure.redis.RedisUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetFoodService implements GetFoodQuery {
    private final String postfix = "*";
    private final FoodPort loadFoodPort;
    private final RedisUtils redisUtils;

    @Override
    public Slice<GetPageableFood> execute(String lastFoodName, String keyword, int size) {
        Slice<Food> foodModels = loadFoodPort.query(lastFoodName, size, keyword);
        List<GetPageableFood> pageableFoods =
                foodModels.getContent().stream()
                        .map(
                                foodModel ->
                                        new GetPageableFood(foodModel.getId(), foodModel.getName()))
                        .collect(Collectors.toList());
        return new SliceImpl<>(pageableFoods, foodModels.getPageable(), foodModels.hasNext());
    }

    @Override
    public GetFoodsResponse execute(String prefix) {
        Set<Object> autoComplete = getAutoComplete(prefix);
        List<String> autoCompleteList =
                autoComplete.stream()
                        .filter(o -> checkPrefixAndPostfix(o, prefix, postfix))
                        .map(o -> (String) o)
                        .limit(10)
                        .toList();
        return GetFoodsResponse.from(autoCompleteList);
    }

    private Set<Object> getAutoComplete(String prefix) {
        Long index = redisUtils.zRank(EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY, prefix);
        return redisUtils.zRange(EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY, index, index + 10);
    }

    private boolean checkPrefixAndPostfix(Object o, String prefix, String postfix) {
        return o instanceof String string && string.startsWith(prefix) && string.endsWith(postfix);
    }
}
