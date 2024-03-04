package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.response.TargetMeal;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.GetTargetMealUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 살 찌기 위한 식단(단,탄,지,칼로리) 정보 조회
 */
@Service
@RequiredArgsConstructor
public class GetTargetMealService implements GetTargetMealUsecase {

    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    @Override
    @Transactional(readOnly = true)
    public TargetMeal execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Member member = memberConverter.toModel(memberEntity);
        return TargetMeal.builder()
                .calorie(member.measureTargetCalorie())
                .carbohydrate(member.measureTargetCarbohydrate())
                .protein(member.measureTargetProtein())
                .fat(member.measureTargetFat())
                .build();
    }
}
