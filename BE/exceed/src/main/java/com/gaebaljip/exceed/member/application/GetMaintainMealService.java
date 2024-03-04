package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.response.MaintainMeal;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 몸무게를 유지하기 위한 식단(단,탄,지,칼로리) 정보 조회
 */

@Service
@RequiredArgsConstructor
public class GetMaintainMealService implements GetMaintainMealUsecase {

    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    @Override
    @Transactional(readOnly = true)
    public MaintainMeal execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Member member = memberConverter.toModel(memberEntity);
        return MaintainMeal.builder()
                .calorie(member.measureTDEE())
                .carbohydrate(member.measureMaintainCarbohydrate())
                .protein(member.measureMaintainProtein())
                .fat(member.measureMaintainFat())
                .build();
    }
}
