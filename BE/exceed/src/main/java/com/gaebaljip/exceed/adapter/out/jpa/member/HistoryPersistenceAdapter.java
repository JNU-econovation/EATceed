package com.gaebaljip.exceed.adapter.out.jpa.member;

import java.time.LocalDateTime;
import java.util.List;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.common.annotation.PersistenceAdapter;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class HistoryPersistenceAdapter implements HistoryPort {
    private final HistoryRepository historyRepository;

    @Override
    public HistoryEntity command(HistoryEntity historyEntity) {
        return historyRepository.save(historyEntity);
    }

    @Override
    public HistoryEntity findMostRecentFutureMember(Long memberId, LocalDateTime date) {
        return historyRepository.findMostRecentFutureMember(memberId, date);
    }

    @Override
    public List<HistoryEntity> findByMemberEntity(MemberEntity memberEntity) {
        return historyRepository.findByMemberEntity(memberEntity);
    }

    @Override
    public void deleteByAllByIdInQuery(List<Long> ids) {
        historyRepository.deleteByAllByIdInQuery(ids);
    }
}
