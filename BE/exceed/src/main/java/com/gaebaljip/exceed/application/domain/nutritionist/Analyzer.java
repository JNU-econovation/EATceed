package com.gaebaljip.exceed.application.domain.nutritionist;

import com.gaebaljip.exceed.application.domain.meal.DailyMeal;
import com.gaebaljip.exceed.application.domain.member.Member;

public abstract class Analyzer implements Analyzable {

    protected DailyMeal dailyMeal;
    protected Member member;

    public Analyzer(DailyMeal dailyMeal, Member member) {
        this.dailyMeal = dailyMeal;
        this.member = member;
    }
}
