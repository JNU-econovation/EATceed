package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public class CarbohydrateAnalyzerFactory extends AbstractAnalyzerFactory {

    private CarbohydrateAnalyzerFactory() {
    }

    private static class SingletonHolder{
        private static final CarbohydrateAnalyzerFactory INSTANCE = new CarbohydrateAnalyzerFactory();
    }

    public static CarbohydrateAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }
    @Override
    public CarbohydrateAnalyzer createAnalyzer(DailyMeal dailyMeal, Member member) {
        return new CarbohydrateAnalyzer(dailyMeal, member);
    }
}
