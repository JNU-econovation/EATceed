package com.gaebaljip.exceed.adapter.out.jpa.food;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.application.domain.food.FoodEntity;
import com.gaebaljip.exceed.application.domain.food.QFoodEntity;
import com.gaebaljip.exceed.common.dto.PageableFoodDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QueryDslFoodRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PageableFoodDTO findPageableFood(String lastFoodName, int size, String keyword) {
        List<FoodEntity> foodEntities =
                jpaQueryFactory
                        .selectFrom(QFoodEntity.foodEntity)
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

    private PageableFoodDTO makePageableMento(List<FoodEntity> foodEntities, int size) {

        boolean hasNext = false;

        if (foodEntities.size() > size) {
            hasNext = true;
            foodEntities.remove(size);
        }

        return new PageableFoodDTO(foodEntities, hasNext, size);
    }
}
