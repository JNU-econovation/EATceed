package com.gaebaljip.exceed.member.application.port.out;

import com.gaebaljip.exceed.common.annotation.Port;
import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;

import java.time.LocalDate;
import java.util.List;

@Port
public interface HistoryPort {
    HistoryEntity command(HistoryEntity historyEntity);
    HistoryEntity findByMemberIdAndDate(Long memberId, LocalDate date);
}
