package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.response.TargetMeal;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;
import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.domain.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetTargetMealService implements GetTargetMealUsecase {

    private final LoadMemberPort loadMemberPort;
    private final MemberConverter memberConverter;

    @Override
    @Transactional(readOnly = true)
    public TargetMeal execute(Long memberId) {
        MemberEntity memberEntity = loadMemberPort.query(memberId);
        MemberModel memberModel = memberConverter.toModel(memberEntity);
        return TargetMeal.builder()
                .calorie(memberModel.measureTargetCalorie())
                .carbohydrate(memberModel.measureTargetCarbohydrate())
                .protein(memberModel.measureTargetProtein())
                .fat(memberModel.measureTargetFat())
                .build();
    }
}
