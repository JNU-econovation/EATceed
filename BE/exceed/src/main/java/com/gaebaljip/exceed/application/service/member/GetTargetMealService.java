package com.gaebaljip.exceed.application.service.member;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.GetTargetMealUsecase;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.TargetMealDTO;

import lombok.RequiredArgsConstructor;

/**
 * 살 찌기 위한 식단(단,탄,지,칼로리) 정보 조회
 *
 * @author : hwangdaesun
 * @version : 1.0
 */
@Service
@RequiredArgsConstructor
public class GetTargetMealService implements GetTargetMealUsecase {

    private final MemberPort memberPort;
    private final HistoryPort historyPort;
    private final MemberConverter memberConverter;

    /**
     * Member 도메인에서 살 찌기 위한 TDEE,단백질,탄수화물,지방을 계산하여 반환한다.
     *
     * @param memberId
     * @return MaintainMeal : 칼로리, 단백질, 탄수화물, 지방에 대한 정보가 들어있다.
     */
    @Override
    @Transactional(readOnly = true)
    public TargetMealDTO execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Member member = memberConverter.toModel(memberEntity);
        return toTargetMeal(member);
    }

    @Override
    public TargetMealDTO execute(Long memberId, LocalDateTime date) {
        Optional<MemberEntity> memberEntity = memberPort.findByIdAndDate(memberId, date);
        if (memberEntity.isPresent()) {
            Member member = memberConverter.toModel(memberEntity.get());
            return toTargetMeal(member);
        } else {
            HistoryEntity lastestHistoryEntity = historyPort.findByMemberIdAndDate(memberId, date);
            Member member = memberConverter.toModel(lastestHistoryEntity);
            return toTargetMeal(member);
        }
    }

    private TargetMealDTO toTargetMeal(Member member) {
        return TargetMealDTO.builder()
                .calorie(member.measureTargetCalorie())
                .carbohydrate(member.measureTargetCarbohydrate())
                .protein(member.measureTargetProtein())
                .fat(member.measureTargetFat())
                .build();
    }
}
