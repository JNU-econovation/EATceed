package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;

/**
 * DailyCarbohydrateAnalyzer 만드는 팩토리 클래스
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class DailyCarbohydrateAnalyzerFactory extends AbstractAnalyzerFactory {

    private DailyCarbohydrateAnalyzerFactory() {}

    private static class SingletonHolder {
        private static final DailyCarbohydrateAnalyzerFactory INSTANCE =
                new DailyCarbohydrateAnalyzerFactory();
    }

    public static DailyCarbohydrateAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public DailyCarbohydrateAnalyzer createAnalyzer(DailyMealFoods dailyMealFoods, Member member) {
        return new DailyCarbohydrateAnalyzer(dailyMealFoods, member);
    }
}
