package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.achieve.application.port.out.LoadMonthTargetPort;
import com.gaebaljip.exceed.achieve.domain.DailyTarget;
import com.gaebaljip.exceed.member.application.MemberConverter;
import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.application.port.out.RecordMemberPort;
import com.gaebaljip.exceed.member.domain.MemberModel;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements LoadMemberPort, RecordMemberPort, LoadMonthTargetPort {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Override
    public MemberEntity query(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public void query(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
    }

    @Override
    public DailyTarget queryForMonthTargets(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        MemberModel memberModel = memberConverter.toModel(memberEntity);
        return getDailyTarget(memberEntity, memberModel);
    }

    private DailyTarget getDailyTarget(MemberEntity memberEntity, MemberModel memberModel) {
        return DailyTarget.builder()
                .targetCalorie(memberModel.measureTargetCalorie())
                .targetCarbohydrate(memberModel.measureTargetCarbohydrate())
                .targetFat(memberModel.measureTargetFat())
                .targetProtein(memberModel.measureTargetProtein())
                .date(memberEntity.getCreatedDate().toLocalDateTime().toLocalDate())
                .build();
    }
}