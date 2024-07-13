package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public class CarbohydrateAnalyzer extends Analyzer {
    public CarbohydrateAnalyzer(DailyMeal dailyMeal, Member member) {
        super(dailyMeal, member);
    }

    @Override
    public boolean analyze() {
        return member.measureTargetCarbohydrate() - dailyMeal.calculateCurrentCarbohydrate() <= 0;
    }
}
