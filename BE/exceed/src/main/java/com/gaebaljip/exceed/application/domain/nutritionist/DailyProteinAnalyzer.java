package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;

/**
 * DailyMeal과 Member를 이용하여 하루치 단백질 달성률을 확인
 *
 * @author hwangdaesun
 * @version 1.0
 */
public class DailyProteinAnalyzer extends DailyMealAnalyzer {
    public DailyProteinAnalyzer(DailyMealFoods dailyMealFoods, Member member) {
        super(dailyMealFoods, member);
    }

    @Override
    public boolean analyze() {
        return member.measureTargetProtein() - dailyMealFoods.calculateCurrentProtein() <= 0;
    }
}
