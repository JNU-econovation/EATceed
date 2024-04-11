package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.common.annotation.PersistenceAdapter;
import com.gaebaljip.exceed.member.application.port.out.HistoryPort;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class HistoryPersistenceAdapter implements HistoryPort {
    private final HistoryRepository historyRepository;

    @Override
    public HistoryEntity command(HistoryEntity historyEntity) {
        return historyRepository.save(historyEntity);
    }

    @Override
    public HistoryEntity findByMemberIdAndDate(Long memberId, LocalDate date) {
        return historyRepository.findByMemberIdAndDate(memberId, date);
    }
}
