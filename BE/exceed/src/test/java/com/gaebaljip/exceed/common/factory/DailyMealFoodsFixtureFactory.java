package com.gaebaljip.exceed.common.factory;

import static org.jeasy.random.FieldPredicates.named;

import java.time.LocalDate;
import java.util.List;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.meal.MealFoodEntity;
import com.gaebaljip.exceed.application.domain.meal.Unit;

public class DailyMealFoodsFixtureFactory {

    /**
     * startDate ~ endDate(양 끝 포함)한 MealFoodEntity를 사용해 DailyMealFoods 생성
     *
     * @param startDate
     * @param endDate
     * @param size
     * @return
     */
    public static DailyMealFoods create(LocalDate startDate, LocalDate endDate, int size) {
        EasyRandomParameters easyRandomParameters =
                new EasyRandomParameters()
                        .scanClasspathForConcreteTypes(true)
                        .dateRange(startDate, endDate)
                        .excludeField(named("updatedDate"))
                        .randomize(Unit.class, new UnitRandomizer());
        EasyRandom easyRandom = new EasyRandom(easyRandomParameters);
        List<MealFoodEntity> mealFoodEntities =
                easyRandom.objects(MealFoodEntity.class, size).toList();
        return new DailyMealFoods(mealFoodEntities);
    }

    private static class UnitRandomizer implements Randomizer<Unit> {
        @Override
        public Unit getRandomValue() {
            Unit unit = new Unit(null, 1.0);
            return unit;
        }
    }
}
