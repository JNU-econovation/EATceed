package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public class CalorieAnalyzerFactory extends AbstractAnalyzerFactory {

    private CalorieAnalyzerFactory() {}

    private static class SingletonHolder {
        private static final CalorieAnalyzerFactory INSTANCE = new CalorieAnalyzerFactory();
    }

    public static CalorieAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public CalorieAnalyzer createAnalyzer(DailyMeal dailyMeal, Member member) {
        return new CalorieAnalyzer(dailyMeal, member);
    }
}
