package com.gaebaljip.exceed.member.application.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.gaebaljip.exceed.common.annotation.Port;
import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

@Port
public interface HistoryPort {
    HistoryEntity command(HistoryEntity historyEntity);

    HistoryEntity findByMemberIdAndDate(Long memberId, LocalDateTime date);

    List<HistoryEntity> findByMemberEntity(MemberEntity memberEntity);

    void deleteByAllByIdInQuery(List<Long> ids);
}
