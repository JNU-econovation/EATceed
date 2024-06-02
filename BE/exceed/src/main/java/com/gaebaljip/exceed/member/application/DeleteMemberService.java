package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.common.annotation.EventPublisherStatus;
import com.gaebaljip.exceed.common.event.DeleteMemberEvent;
import com.gaebaljip.exceed.common.event.Events;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.DeleteMemberUseCase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteMemberService implements DeleteMemberUseCase {
    private final MemberPort memberPort;

    @Override
    @Transactional
    @EventPublisherStatus
    public void execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Events.raise(DeleteMemberEvent.from(memberEntity));
        memberPort.delete(memberEntity);
    }
}
