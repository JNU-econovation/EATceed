package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.TargetMeal;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;
import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.MemberModel;
import com.gaebaljip.exceed.member.domain.PhysiqueModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTargetMealService implements GetTargetMealUsecase {

    private final LoadMemberPort loadMemberPort;

    @Override
    public TargetMeal execute(Long memberId) {
        loadMemberPort.loadMember(memberId);
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
        TargetMeal targetMeal = TargetMeal.builder()
                .targetCalorie(memberModel.getPhysiqueModel().measureTargetCalorie())
                .targetCarbohydrate(memberModel.getPhysiqueModel().measureTargetCarbohydrate())
                .targetProtein(memberModel.getPhysiqueModel().measureTargetProtein())
                .targetFat(memberModel.getPhysiqueModel().measureTargetFat())
                .build();
        return targetMeal;
    }
}
