package com.gaebaljip.exceed.infrastructure.sse.adapter.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

public interface NotifyRepository extends JpaRepository<NotifyEntity, Long> {
    List<NotifyEntity> findByReceiver(MemberEntity memberEntity);

    @Query("delete from NotifyEntity n where n.id in :ids")
    @Modifying
    @Transactional
    void deleteByAllByIdInQuery(List<Long> ids);
}
