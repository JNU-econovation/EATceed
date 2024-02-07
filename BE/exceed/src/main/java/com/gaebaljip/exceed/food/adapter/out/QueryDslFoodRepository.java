package com.gaebaljip.exceed.food.adapter.out;

import com.gaebaljip.exceed.dto.response.PageableFood;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryDslFoodRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PageableFood findPageableFood(String lastFoodName, int size, String keyword) {
        List<FoodEntity> foodEntities = jpaQueryFactory.selectFrom(QFoodEntity.foodEntity)
                .where(greaterThanFoodName(lastFoodName), containsFoodName(keyword))
                .orderBy(QFoodEntity.foodEntity.name.asc())
                .limit(size + 1)
                .fetch();
        return makePageableMento(foodEntities, size);
    }

    private BooleanExpression greaterThanFoodName(String lastFoodName) {
        if (lastFoodName == null) {
            return null;
        }
        return QFoodEntity.foodEntity.name.gt(lastFoodName);
    }

    private BooleanExpression containsFoodName(String foodName) {
        if (foodName == null) {
            return null;
        }
        return QFoodEntity.foodEntity.name.contains(foodName);
    }

    private PageableFood makePageableMento(List<FoodEntity> foodEntities, int size) {

        boolean hasNext = false;

        if (foodEntities.size() > size) {
            hasNext = true;
            foodEntities.remove(size);
        }

        return new PageableFood(foodEntities, hasNext, size);

    }
}
