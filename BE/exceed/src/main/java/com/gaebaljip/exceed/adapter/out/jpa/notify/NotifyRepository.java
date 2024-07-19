package com.gaebaljip.exceed.adapter.out.jpa.notify;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.domain.notify.NotifyEntity;

public interface NotifyRepository extends JpaRepository<NotifyEntity, Long> {
    List<NotifyEntity> findByReceiver(MemberEntity memberEntity);

    @Query("delete from NotifyEntity n where n.id in :ids")
    @Modifying
    @Transactional
    void deleteByAllByIdInQuery(List<Long> ids);
}
