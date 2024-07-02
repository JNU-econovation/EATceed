package com.gaebaljip.exceed.meal.adapter.out;

import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.*;

import com.gaebaljip.exceed.common.BaseEntity;
import com.gaebaljip.exceed.dto.request.EatMealFoodDTO;
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

    @Column(name = ENTITY_PREFIX + "_MULTIPLE")
    private Double multiple;

    @Column(name = ENTITY_PREFIX + "_G")
    private Integer g;

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
                                                .g(eatMealFoodDTOS.get(i).g())
                                                .multiple(eatMealFoodDTOS.get(i).multiple())
                                                .foodEntity(foodEntities.get(i))
                                                .mealEntity(mealEntity)
                                                .build())
                        .toList();
        return mealFoodEntities;
    }
}
