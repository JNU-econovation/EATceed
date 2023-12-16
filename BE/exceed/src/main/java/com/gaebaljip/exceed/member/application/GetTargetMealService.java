package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.TargetMeal;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;
import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTargetMealService implements GetTargetMealUsecase {

    private final LoadMemberPort loadMemberPort;

    @Override
    public TargetMeal execute(Long memberId) {
        loadMemberPort.query(memberId);
        MemberModel memberModel = MemberModel.builder()
                .gender(1)
                .age(25)
                .activity(Activity.NOT_ACTIVE)
                .height(171.2)
                .weight(60.2)
                .build();
        TargetMeal targetMeal = TargetMeal.builder()
                .targetCalorie(memberModel.measureTargetCalorie())
                .targetCarbohydrate(memberModel.measureTargetCarbohydrate())
                .targetProtein(memberModel.measureTargetProtein())
                .targetFat(memberModel.measureTargetFat())
                .build();
        return targetMeal;
    }
}
