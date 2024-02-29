package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyTargetPort;
import com.gaebaljip.exceed.member.application.MemberConverter;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Member;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPort, MonthlyTargetPort {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Override
    public MemberEntity query(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
    @Override
    public MemberEntity command(MemberEntity memberEntity) {
        return memberRepository.save(memberEntity);
    }

    /**
     * 회원 수정 기능 + 카카오 로그인 도입 후 Map<LocalDate date, MemberModel> 변경
     */
    @Override
    public Member query(Long memberId, LocalDate date) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return memberConverter.toModel(memberEntity);
    }
}