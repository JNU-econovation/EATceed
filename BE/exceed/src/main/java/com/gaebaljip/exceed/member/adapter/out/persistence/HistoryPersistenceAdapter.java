package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.common.annotation.PersistenceAdapter;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.member.application.port.out.HistoryPort;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class HistoryPersistenceAdapter implements HistoryPort {
    private final HistoryRepository historyRepository;

    @Override
    public HistoryEntity command(HistoryEntity historyEntity) {
        return historyRepository.save(historyEntity);
    }
}
