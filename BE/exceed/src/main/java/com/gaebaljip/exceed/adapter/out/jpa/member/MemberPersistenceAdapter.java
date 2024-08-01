package com.gaebaljip.exceed.adapter.out.jpa.member;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.application.service.member.MemberConverter;
import com.gaebaljip.exceed.common.exception.auth.MemberNotCheckedException;
import com.gaebaljip.exceed.common.exception.member.MemberNotFoundException;
import com.gaebaljip.exceed.common.exception.member.NotFoundHistoryException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPort {

    private final MemberRepository memberRepository;
    private final HistoryRepository historyRepository;
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
        Boolean checked = memberRepository.findCheckedByEmail(email);
        return checked != null ? checked : false;
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
    public void delete(MemberEntity memberEntity) {
        memberRepository.delete(memberEntity);
    }

    @Override
    public Member findMemberByDate(Long memberId, LocalDateTime dateTime) {
        Optional<MemberEntity> memberEntity = findMemberBeforeDate(memberId, dateTime);
        if (memberEntity.isPresent()) {
            return memberConverter.toModel(memberEntity.get());
        } else {
            HistoryEntity lastestHistoryEntity =
                    historyRepository
                            .findRecentFutureMember(memberId, dateTime)
                            .orElseThrow(() -> NotFoundHistoryException.EXECPTION);
            return memberConverter.toModel(lastestHistoryEntity);
        }
    }

    private Optional<MemberEntity> findMemberBeforeDate(Long memberId, LocalDateTime date) {
        return memberRepository.findMemberBeforeDate(memberId, date);
    }
}
