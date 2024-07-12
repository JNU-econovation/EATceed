package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;

@Repository
public interface CustomHistoryRepository {
    HistoryEntity findByMemberIdAndDate(Long memberId, LocalDateTime date);
}
