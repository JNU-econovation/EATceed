package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.MaintainMeal;
import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.domain.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMaintainMealService implements GetMaintainMealUsecase {

    private final LoadMemberPort loadMemberPort;

    @Override
    public MaintainMeal execute(Long memberId) {
        loadMemberPort.query(memberId);
        MemberModel memberModel = MemberModel.builder()
                .gender(1)
                .age(25)
                .activity(Activity.NOT_ACTIVE)
                .height(171.2)
                .weight(60.2)
                .build();
        MaintainMeal maintainMeal = MaintainMeal.builder()
                .maintainCalorie(memberModel.measureTDEE())
                .maintainCarbohydrate(memberModel.measureMaintainCarbohydrate())
                .maintainProtein(memberModel.measureMaintainProtein())
                .maintainFat(memberModel.measureMaintainFat())
                .build();
        return maintainMeal;
    }
}
