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

    /**
     * 특정 월의 회원 기록을 찾는다. HISTORY_TB에서 특정 월의 기록이 있을 경우, HISTORY_TB에서 특정 월의 기록을 찾는다.(historyEntities).
     * 그리고, HISTORY_TB에서 특정 월의 가장 최신 기록(recentHistory)과 비교하여 가장 최신에 있는 기록을 찾는다. 만약 없을 경우 가장 최신 기록인
     * MEMBER_TB의 정보를 사용한다. / HISTORY_TB에서 특정 월의 기록이 없을 경우 가장 최신 기록인 MEMBER_TB의 정보를 사용한다.
     *
     * @param memberId
     * @param date
     * @return
     */
    @Override
    public Map<LocalDate, Member> findMembersByMonth(Long memberId, LocalDate date) {
        Map<LocalDate, Member> members = new HashMap<>();

        LocalDateTime startDateTime = date.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDateTime = date.withDayOfMonth(date.lengthOfMonth()).atTime(23, 59, 59);

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
            HistoryEntity recentHistory = historyEntities.get(historyEntities.size() - 1);
            Optional<HistoryEntity> recentFutureHistory =
                    historyRepository.findRecentFutureMember(
                            memberId, recentHistory.getCreatedDate());
            if (recentFutureHistory.isEmpty()) {
                MemberEntity memberEntity =
                        memberRepository
                                .findById(memberId)
                                .orElseThrow(() -> MemberNotFoundException.EXECPTION);
                members.put(endDateTime.toLocalDate(), memberConverter.toModel(memberEntity));
            } else {
                members.put(
                        endDateTime.toLocalDate(),
                        memberConverter.toModel(recentFutureHistory.get()));
            }
        }
        return members;
    }

    /**
     * startDate부터 endDate까지의 회원 기록을 찾는다.
     *
     * @param memberId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public Map<LocalDate, Member> findMembersByDays(
            Long memberId, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Member> members = new HashMap<>();

        LocalDateTime startDateTime = startDate.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDateTime =
                endDate.withDayOfMonth(endDate.lengthOfMonth()).atTime(23, 59, 59);

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
            HistoryEntity recentHistory = historyEntities.get(historyEntities.size() - 1);
            Optional<HistoryEntity> recentFutureHistory =
                    historyRepository.findRecentFutureMember(
                            memberId, recentHistory.getCreatedDate());
            if (recentFutureHistory.isEmpty()) {
                MemberEntity memberEntity =
                        memberRepository
                                .findById(memberId)
                                .orElseThrow(() -> MemberNotFoundException.EXECPTION);
                members.put(endDateTime.toLocalDate(), memberConverter.toModel(memberEntity));
            } else {
                members.put(
                        endDateTime.toLocalDate(),
                        memberConverter.toModel(recentFutureHistory.get()));
            }
        }
        return members;
    }

    @Override
    public void deleteByAllByIdInQuery(List<Long> ids) {
        historyRepository.deleteByAllByIdInQuery(ids);
    }
}
