package com.gaebaljip.exceed.member.domain;

import com.gaebaljip.exceed.common.DecimalUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhysiqueModelTest {

    @Test
    void measureBMR() {
        //given
        PhysiqueModel physiqueModel = createPhysiqueModel(171, 61, 25, Activity.NOT_ACTIVE, true);

        //when
        double bmr = physiqueModel.measureBMR();
        double roundedBmr = DecimalUtil.roundToTwoDecimals(bmr);

        //then
        assertEquals(1592.01, roundedBmr);
    }

    @Test
    void measureTDEE() {
        //given
        PhysiqueModel physiqueModel = createPhysiqueModel(171, 61, 25, Activity.NOT_ACTIVE, true);

        //when
        double tdee = physiqueModel.measureTDEE();
        double roundedTdee = DecimalUtil.roundToTwoDecimals(tdee);
        //then
        assertEquals(1910.42, roundedTdee);
    }

    @Test
    void measureTargetCalorie() {
        //given
        PhysiqueModel physiqueModel = createPhysiqueModel(171, 61, 25, Activity.NOT_ACTIVE, true);

        //when
        double targetCalorie = physiqueModel.measureTargetCalorie();
        double roundedTargetCalorie = DecimalUtil.roundToTwoDecimals(targetCalorie);
        //then
        assertEquals(2410.42, roundedTargetCalorie);
    }

    private PhysiqueModel createPhysiqueModel(double height, double weight, int age, Activity activity, Boolean gender) {
        return PhysiqueModel.builder()
                .height(height)
                .weight(weight)
                .age(age)
                .activity(activity)
                .gender(gender)
                .build();
    }
}