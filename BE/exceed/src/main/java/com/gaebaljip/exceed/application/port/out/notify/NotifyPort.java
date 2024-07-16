package com.gaebaljip.exceed.application.port.out.notify;

import java.util.List;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.domain.notify.NotifyEntity;
import com.gaebaljip.exceed.common.annotation.Port;

@Port
public interface NotifyPort {
    List<NotifyEntity> findByMemberEntity(MemberEntity memberEntity);

    void deleteByAllByIdInQuery(List<Long> ids);

    NotifyEntity command(NotifyEntity notifyEntity);
}
