package com.gaebaljip.exceed.application.port.out.member;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.annotation.Port;

@Port
public interface HistoryPort {
    HistoryEntity command(HistoryEntity historyEntity);

    List<HistoryEntity> findByMemberEntity(MemberEntity memberEntity);

    Map<LocalDate, Member> findMembersByMonth(Long memberId, LocalDate date);

    Map<LocalDate, Member> findMembersByDays(Long memberId, LocalDate startDate, LocalDate endDate);

    void deleteByAllByIdInQuery(List<Long> ids);
}
