package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public class ProteinAnalyzerFactory extends AbstractAnalyzerFactory {

    private ProteinAnalyzerFactory() {}

    private static class SingletonHolder {
        private static final ProteinAnalyzerFactory INSTANCE = new ProteinAnalyzerFactory();
    }

    public static ProteinAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public ProteinAnalyzer createAnalyzer(DailyMeal dailyMeal, Member member) {
        return new ProteinAnalyzer(dailyMeal, member);
    }
}
