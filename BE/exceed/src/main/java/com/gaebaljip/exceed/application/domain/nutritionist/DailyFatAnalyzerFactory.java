package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;

/**
 * DailyFatAnalyzer 만드는 팩토리 클래스
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class DailyFatAnalyzerFactory extends AbstractAnalyzerFactory {

    private DailyFatAnalyzerFactory() {}

    private static class SingletonHolder {
        private static final DailyFatAnalyzerFactory INSTANCE = new DailyFatAnalyzerFactory();
    }

    public static DailyFatAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public DailyFatAnalyzer createAnalyzer(DailyMealFoods dailyMealFoods, Member member) {
        return new DailyFatAnalyzer(dailyMealFoods, member);
    }
}
