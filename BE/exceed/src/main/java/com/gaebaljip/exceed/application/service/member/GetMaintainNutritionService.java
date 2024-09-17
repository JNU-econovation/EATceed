package com.gaebaljip.exceed.application.service.member;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.GetMaintainNutritionUsecase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.MaintainMealDTO;

import lombok.RequiredArgsConstructor;

/**
 * 몸무게를 유지하기 위한 식단(단백질,탄수화물,지방,칼로리) 정보 조회한다.
 *
 * @author : hwangdaesun
 * @version : 1.0
 */
@Service
@RequiredArgsConstructor
public class GetMaintainNutritionService implements GetMaintainNutritionUsecase {

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
    public MaintainMealDTO execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Member member = memberConverter.toModel(memberEntity);
        return toMaintainMeal(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintainMealDTO execute(Long memberId, LocalDateTime dateTime) {
        return toMaintainMeal(memberPort.findMemberByDate(memberId, dateTime));
    }

    private MaintainMealDTO toMaintainMeal(Member member) {
        return MaintainMealDTO.builder()
                .calorie(member.measureTDEE())
                .carbohydrate(member.measureMaintainCarbohydrate())
                .protein(member.measureMaintainProtein())
                .fat(member.measureMaintainFat())
                .build();
    }
}
