package com.gaebaljip.exceed.meal.adapter.out;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.gaebaljip.exceed.common.BaseEntity;
import com.gaebaljip.exceed.food.adapter.out.FoodEntity;
import com.gaebaljip.exceed.food.exception.FoodNotFoundException;
import com.gaebaljip.exceed.nutritionist.exception.MealNotFoundException;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = MealFoodEntity.ENTITY_PREFIX + "_TB")
@Builder(toBuilder = true)
public class MealFoodEntity extends BaseEntity {

    public static final String ENTITY_PREFIX = "MEAL_FOOD";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_PK", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEAL_FK", referencedColumnName = "MEAL_PK")
    private MealEntity mealEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_FK", referencedColumnName = "FOOD_PK")
    private FoodEntity foodEntity;

    public static List<MealFoodEntity> createMealFoods(
            List<FoodEntity> foodEntities, MealEntity mealEntity) {
        if (foodEntities.isEmpty()) {
            throw FoodNotFoundException.Exception;
        }
        if (mealEntity == null) {
            throw MealNotFoundException.EXECPTION;
        }
        List<MealFoodEntity> mealFoodEntities = new ArrayList<>();
        for (FoodEntity foodEntity : foodEntities) {
            mealFoodEntities.add(
                    MealFoodEntity.builder().foodEntity(foodEntity).mealEntity(mealEntity).build());
        }
        return mealFoodEntities;
    }

    private MealFoodEntity createMealFood(FoodEntity foodEntity) {
        return MealFoodEntity.builder().foodEntity(foodEntity).build();
    }
}
