package com.gaebaljip.exceed.adapter.out.jpa.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.out.jpa.member.custom.CustomHistoryRepository;
import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

public interface HistoryRepository
        extends JpaRepository<HistoryEntity, Long>, CustomHistoryRepository {
    void deleteByMemberEntity(MemberEntity memberEntity);

    List<HistoryEntity> findByMemberEntity(MemberEntity memberEntity);

    @Query("delete from HistoryEntity h where h.id in :ids")
    @Modifying
    @Transactional
    void deleteByAllByIdInQuery(List<Long> ids);
}
