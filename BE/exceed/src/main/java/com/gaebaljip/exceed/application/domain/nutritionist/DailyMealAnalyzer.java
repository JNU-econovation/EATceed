package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public abstract class DailyMealAnalyzer implements Analyzable {

    protected DailyMeal dailyMeal;
    protected Member member;

    public DailyMealAnalyzer(DailyMeal dailyMeal, Member member) {
        this.dailyMeal = dailyMeal;
        this.member = member;
    }
}
