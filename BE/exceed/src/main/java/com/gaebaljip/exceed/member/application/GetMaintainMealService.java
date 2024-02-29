package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.response.MaintainMeal;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMaintainMealService implements GetMaintainMealUsecase {

    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    @Override
    @Transactional(readOnly = true)
    public MaintainMeal execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Member memberModel = memberConverter.toModel(memberEntity);
        return MaintainMeal.builder()
                .calorie(memberModel.measureTDEE())
                .carbohydrate(memberModel.measureMaintainCarbohydrate())
                .protein(memberModel.measureMaintainProtein())
                .fat(memberModel.measureMaintainFat())
                .build();
    }
}
