package com.gaebaljip.exceed.member.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.auth.exception.MemberNotCheckedException;
import com.gaebaljip.exceed.member.application.MemberConverter;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Member;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;
import com.gaebaljip.exceed.nutritionist.application.port.out.MonthlyTargetPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPort, MonthlyTargetPort {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Override
    public MemberEntity query(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> MemberNotFoundException.EXECPTION);
    }

    @Override
    public MemberEntity command(MemberEntity memberEntity) {
        return memberRepository.save(memberEntity);
    }

    /** 회원 수정 기능 구현 후 -> Map<LocalDate date, MemberModel> 변경 */
    @Override
    public Member query(Long memberId, LocalDateTime date) {
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> MemberNotFoundException.EXECPTION);
        return memberConverter.toModel(memberEntity);
    }

    @Override
    public Boolean isChecked(String email) {
        return memberRepository.findCheckedByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public MemberEntity findMemberByEmail(String email) {
        return memberRepository
                .findByEmail(email)
                .orElseThrow(() -> MemberNotFoundException.EXECPTION);
    }

    @Override
    public MemberEntity findCheckedMemberByEmail(String email) {
        MemberEntity member =
                memberRepository
                        .findByEmail(email)
                        .orElseThrow(() -> MemberNotFoundException.EXECPTION);
        if (!member.getChecked()) {
            throw MemberNotCheckedException.EXECPTION;
        }
        return member;
    }

    @Override
    public Optional<MemberEntity> findByIdAndDate(Long memberId, LocalDateTime date) {
        return memberRepository.findByIdAndDate(memberId, date);
    }
}
