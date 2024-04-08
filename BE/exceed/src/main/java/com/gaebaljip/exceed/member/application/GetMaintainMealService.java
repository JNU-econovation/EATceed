package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.dto.response.MaintainMeal;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.GetMaintainMealUsecase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Member;

import lombok.RequiredArgsConstructor;

/**
 * 몸무게를 유지하기 위한 식단(단백질,탄수화물,지방,칼로리) 정보 조회한다.
 *
 * @author : hwangdaesun
 * @version : 1.0
 */
@Service
@RequiredArgsConstructor
public class GetMaintainMealService implements GetMaintainMealUsecase {

    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    /**
     * Member 도메인에서 몸무게를 유지하기 위한 TDEE,단백질,탄수화물,지방을 계산하여 반환한다.
     *
     * @param memberId
     * @return MaintainMeal : 칼로리, 단백질, 탄수화물, 지방에 대한 정보가 들어있다.
     */
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
