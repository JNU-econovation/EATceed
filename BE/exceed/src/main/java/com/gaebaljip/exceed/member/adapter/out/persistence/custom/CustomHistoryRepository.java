package com.gaebaljip.exceed.member.adapter.out.persistence.custom;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;

@Repository
public interface CustomHistoryRepository {
    HistoryEntity findByMemberIdAndDate(Long memberId, LocalDateTime date);
}
