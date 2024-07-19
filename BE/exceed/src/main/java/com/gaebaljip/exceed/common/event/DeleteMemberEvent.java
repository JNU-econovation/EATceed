package com.gaebaljip.exceed.common.event;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteMemberEvent extends DomainEvent {
    private MemberEntity memberEntity;

    public static DeleteMemberEvent from(MemberEntity memberEntity) {
        return DeleteMemberEvent.builder().memberEntity(memberEntity).build();
    }
}
