package com.gaebaljip.exceed.member.domain;

import com.gaebaljip.exceed.common.DecimalUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void measureBMR() {
        //given
        Member memberModel = createPhysiqueModel(171, 61, 25, Activity.NOT_ACTIVE, 1);

        //when
        double bmr = memberModel.measureBMR();
        double roundedBmr = DecimalUtil.roundToTwoDecimals(bmr);

        //then
        assertEquals(1592.01, roundedBmr);
    }

    @Test
    void measureTDEE() {
        //given
        Member memberModel = createPhysiqueModel(171, 61, 25, Activity.NOT_ACTIVE, 1);

        //when
        double tdee = memberModel.measureTDEE();
        double roundedTdee = DecimalUtil.roundToTwoDecimals(tdee);
        //then
        assertEquals(1910.42, roundedTdee);
    }

    @Test
    void measureTargetCalorie() {
        //given
        Member memberModel = createPhysiqueModel(171, 61, 25, Activity.NOT_ACTIVE, 1);

        //when
        double targetCalorie = memberModel.measureTargetCalorie();
        double roundedTargetCalorie = DecimalUtil.roundToTwoDecimals(targetCalorie);
        //then
        assertEquals(2410.42, roundedTargetCalorie);
    }

    private Member createPhysiqueModel(double height, double weight, int age, Activity activity, int gender) {
        return Member.builder()
                .height(height)
                .weight(weight)
                .age(age)
                .activity(activity)
                .gender(gender)
                .build();
    }
}