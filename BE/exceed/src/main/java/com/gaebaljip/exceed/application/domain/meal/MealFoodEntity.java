package com.gaebaljip.exceed.application.domain.meal;

import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.*;

import com.gaebaljip.exceed.application.domain.food.FoodEntity;
import com.gaebaljip.exceed.common.BaseEntity;
import com.gaebaljip.exceed.common.dto.EatMealFoodDTO;
import com.gaebaljip.exceed.common.exception.food.FoodNotFoundException;
import com.gaebaljip.exceed.common.exception.nutritionist.MealNotFoundException;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Embedded private Unit unit;

    public static List<MealFoodEntity> createMealFoods(
            List<FoodEntity> foodEntities,
            MealEntity mealEntity,
            List<EatMealFoodDTO> eatMealFoodDTOS) {
        if (foodEntities.isEmpty()) {
            throw FoodNotFoundException.Exception;
        }
        if (mealEntity == null) {
            throw MealNotFoundException.EXECPTION;
        }
        List<MealFoodEntity> mealFoodEntities =
                IntStream.range(0, foodEntities.size())
                        .mapToObj(
                                i ->
                                        MealFoodEntity.builder()
                                                .unit(
                                                        new Unit(
                                                                eatMealFoodDTOS.get(i).g(),
                                                                eatMealFoodDTOS.get(i).multiple()))
                                                .foodEntity(foodEntities.get(i))
                                                .mealEntity(mealEntity)
                                                .build())
                        .toList();
        return mealFoodEntities;
    }

    @Override
    public String toString() {
        return "MealFoodEntity{"
                + "id="
                + id
                + ", multiple="
                + unit.getMultiple()
                + ", g="
                + unit.getG()
                + '}';
    }

    public double getAdjustedCalorie() {
        return this.unit.getStrategy().measure(this.foodEntity.getCalorie(), unit);
    }

    public double getAdjustedCarbohydrate() {
        return unit.getStrategy().measure(this.foodEntity.getCarbohydrate(), unit);
    }

    public double getAdjustedProtein() {
        return unit.getStrategy().measure(this.foodEntity.getProtein(), unit);
    }

    public double getAdjustedFat() {
        return unit.getStrategy().measure(this.foodEntity.getFat(), unit);
    }
}
