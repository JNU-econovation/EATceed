package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public class FatAnalyzer extends Analyzer {
    public FatAnalyzer(DailyMeal dailyMeal, Member member) {
        super(dailyMeal, member);
    }

    @Override
    public boolean analyze() {
        return member.measureTargetFat() - dailyMeal.calculateCurrentFat() <= 0;
    }
}
