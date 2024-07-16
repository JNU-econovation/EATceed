package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.DeleteMemberUseCase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.annotation.EventPublisherStatus;
import com.gaebaljip.exceed.common.event.DeleteMemberEvent;
import com.gaebaljip.exceed.common.event.Events;

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
