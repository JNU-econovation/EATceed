package com.gaebaljip.exceed.adapter.out.jpa.member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.application.service.member.MemberConverter;
import com.gaebaljip.exceed.common.annotation.PersistenceAdapter;
import com.gaebaljip.exceed.common.annotation.Timer;
import com.gaebaljip.exceed.common.exception.member.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class HistoryPersistenceAdapter implements HistoryPort {
    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Override
    public HistoryEntity command(HistoryEntity historyEntity) {
        return historyRepository.save(historyEntity);
    }

    @Override
    public List<HistoryEntity> findByMemberEntity(MemberEntity memberEntity) {
        return historyRepository.findByMemberEntity(memberEntity);
    }

    @Override
    @Timer
    public Map<LocalDate, Member> findMembersByMonth(Long memberId, LocalDateTime dateTime) {
        Map<LocalDate, Member> members = new HashMap<>();

        LocalDateTime startDateTime = dateTime.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDateTime =
                dateTime.withDayOfMonth(dateTime.toLocalDate().lengthOfMonth())
                        .toLocalDate()
                        .atTime(23, 59, 59);

        List<HistoryEntity> historyEntities =
                historyRepository.findMembersBetweenDate(memberId, startDateTime, endDateTime);
        if (historyEntities.isEmpty()) {
            MemberEntity memberEntity =
                    memberRepository
                            .findById(memberId)
                            .orElseThrow(() -> MemberNotFoundException.EXECPTION);
            Member member = memberConverter.toModel(memberEntity);
            members.put(memberEntity.getUpdatedDate().toLocalDate(), member);
        } else {
            historyEntities.stream()
                    .forEach(
                            historyEntity ->
                                    members.put(
                                            historyEntity.getCreatedDate().toLocalDate(),
                                            memberConverter.toModel(historyEntity)));
            HistoryEntity pastHistoryEntity = historyEntities.get(historyEntities.size() - 1);
            Optional<HistoryEntity> recentFutureMember =
                    historyRepository.findRecentFutureMember(
                            memberId, pastHistoryEntity.getCreatedDate());
            if (recentFutureMember.isEmpty()) {
                MemberEntity memberEntity =
                        memberRepository
                                .findById(memberId)
                                .orElseThrow(() -> MemberNotFoundException.EXECPTION);
                members.put(endDateTime.toLocalDate(), memberConverter.toModel(memberEntity));
            } else {
                members.put(
                        endDateTime.toLocalDate(),
                        memberConverter.toModel(recentFutureMember.get()));
            }
        }
        return members;
    }

    @Override
    public void deleteByAllByIdInQuery(List<Long> ids) {
        historyRepository.deleteByAllByIdInQuery(ids);
    }
}
