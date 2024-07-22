package com.gaebaljip.exceed.application.port.out.member;

import java.time.LocalDateTime;
import java.util.List;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.annotation.Port;

@Port
public interface HistoryPort {
    HistoryEntity command(HistoryEntity historyEntity);

    HistoryEntity findMostRecentFutureMember(Long memberId, LocalDateTime date);

    List<HistoryEntity> findByMemberEntity(MemberEntity memberEntity);

    void deleteByAllByIdInQuery(List<Long> ids);
}
