package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public class FatAnalyzerFactory extends AbstractAnalyzerFactory {

    private FatAnalyzerFactory() {}

    private static class SingletonHolder {
        private static final FatAnalyzerFactory INSTANCE = new FatAnalyzerFactory();
    }

    public static FatAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public FatAnalyzer createAnalyzer(DailyMeal dailyMeal, Member member) {
        return new FatAnalyzer(dailyMeal, member);
    }
}
