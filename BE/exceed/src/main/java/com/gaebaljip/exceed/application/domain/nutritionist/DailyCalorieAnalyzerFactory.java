package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;

/**
 * DailyCalorieAnalyzer 만드는 팩토리 클래스
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class DailyCalorieAnalyzerFactory extends AbstractAnalyzerFactory {

    private DailyCalorieAnalyzerFactory() {}

    private static class SingletonHolder {
        private static final DailyCalorieAnalyzerFactory INSTANCE =
                new DailyCalorieAnalyzerFactory();
    }

    public static DailyCalorieAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public DailyCalorieAnalyzer createAnalyzer(DailyMealFoods dailyMealFoods, Member member) {
        return new DailyCalorieAnalyzer(dailyMealFoods, member);
    }
}
