package com.gaebaljip.exceed.infrastructure.sse.application.port.out;

import java.util.List;

import com.gaebaljip.exceed.common.annotation.Port;
import com.gaebaljip.exceed.infrastructure.sse.adapter.out.NotifyEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;

@Port
public interface NotifyPort {
    List<NotifyEntity> findByMemberEntity(MemberEntity memberEntity);

    void deleteByAllByIdInQuery(List<Long> ids);

    NotifyEntity command(NotifyEntity notifyEntity);
}
