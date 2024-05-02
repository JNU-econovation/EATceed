package com.gaebaljip.exceed.member.application.port.out;

import java.time.LocalDateTime;

import com.gaebaljip.exceed.common.annotation.Port;
import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;

@Port
public interface HistoryPort {
    HistoryEntity command(HistoryEntity historyEntity);

    HistoryEntity findByMemberIdAndDate(Long memberId, LocalDateTime date);
}
