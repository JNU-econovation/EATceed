package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;
/**
 * DailyProteinAnalyzer 만드는 팩토리 클래스
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class DailyProteinAnalyzerFactory extends AbstractAnalyzerFactory {

    private DailyProteinAnalyzerFactory() {}

    private static class SingletonHolder {
        private static final DailyProteinAnalyzerFactory INSTANCE = new DailyProteinAnalyzerFactory();
    }

    public static DailyProteinAnalyzerFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public DailyProteinAnalyzer createAnalyzer(DailyMeal dailyMeal, Member member) {
        return new DailyProteinAnalyzer(dailyMeal, member);
    }
}
