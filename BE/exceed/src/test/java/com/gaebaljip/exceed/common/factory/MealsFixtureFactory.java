package com.gaebaljip.exceed.common.factory;

import java.time.LocalDate;
import java.util.List;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;

import com.gaebaljip.exceed.application.domain.meal.Meal;
import com.gaebaljip.exceed.application.domain.meal.Unit;

public class MealsFixtureFactory {
    public static List<Meal> create(LocalDate startDate, LocalDate endDate, int mealSize) {
        EasyRandomParameters easyRandomParameters =
                new EasyRandomParameters()
                        .scanClasspathForConcreteTypes(true)
                        .dateRange(startDate, endDate)
                        .randomize(Unit.class, new UnitRandomizer());
        EasyRandom easyRandom = new EasyRandom(easyRandomParameters);
        return easyRandom.objects(Meal.class, mealSize).toList();
    }

    private static class UnitRandomizer implements Randomizer<Unit> {
        @Override
        public Unit getRandomValue() {
            Unit unit = Unit.builder().g(null).multiple(1.0).build();
            return unit;
        }
    }
}
