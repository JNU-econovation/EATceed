package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;

public abstract class DailyMealAnalyzer implements Analyzable {

    protected DailyMealFoods dailyMealFoods;
    protected Member member;

    public DailyMealAnalyzer(DailyMealFoods dailyMealFoods, Member member) {
        this.dailyMealFoods = dailyMealFoods;
        this.member = member;
    }
}
