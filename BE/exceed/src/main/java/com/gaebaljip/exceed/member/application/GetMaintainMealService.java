package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.MaintainMeal;
import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.MemberModel;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.domain.PhysiqueModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMaintainMealService implements GetMaintainMealUsecase {

    private final LoadMemberPort loadMemberPort;

    @Override
    public MaintainMeal execute(Long memberId) {
        loadMemberPort.query(memberId);
        PhysiqueModel physiqueModel = PhysiqueModel.builder()
                .gender(true)
                .age(25)
                .activity(Activity.NOT_ACTIVE)
                .height(171.2)
                .weight(60.2)
                .build();
        MemberModel memberModel = MemberModel.builder()
                .physiqueModel(physiqueModel)
                .etc("뭐든 다 잘먹음")
                .build();
        MaintainMeal maintainMeal = MaintainMeal.builder()
                .maintainCalorie(memberModel.getPhysiqueModel().measureTDEE())
                .maintainCarbohydrate(memberModel.getPhysiqueModel().measureMaintainCarbohydrate())
                .maintainProtein(memberModel.getPhysiqueModel().measureMaintainProtein())
                .maintainFat(memberModel.getPhysiqueModel().measureMaintainFat())
                .build();
        return maintainMeal;
    }
}
