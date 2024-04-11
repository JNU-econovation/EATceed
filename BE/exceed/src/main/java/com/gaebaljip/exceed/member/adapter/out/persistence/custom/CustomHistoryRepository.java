package com.gaebaljip.exceed.member.adapter.out.persistence.custom;

import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomHistoryRepository {
    HistoryEntity findByMemberIdAndDate(Long memberId, LocalDate date);
}
