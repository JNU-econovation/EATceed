package com.gaebaljip.exceed.adapter.out.jpa.member.custom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;

@Repository
public interface CustomHistoryRepository {
    Optional<HistoryEntity> findRecentFutureMember(Long memberId, LocalDateTime date);

    List<HistoryEntity> findMembersBetweenDate(
            Long memberId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
