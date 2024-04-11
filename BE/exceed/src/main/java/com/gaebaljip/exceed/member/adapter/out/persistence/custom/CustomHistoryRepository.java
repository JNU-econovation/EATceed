package com.gaebaljip.exceed.member.adapter.out.persistence.custom;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;

@Repository
public interface CustomHistoryRepository {
    HistoryEntity findByMemberIdAndDate(Long memberId, LocalDate date);
}
