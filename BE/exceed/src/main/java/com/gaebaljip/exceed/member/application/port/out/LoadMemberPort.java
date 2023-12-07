package com.gaebaljip.exceed.member.application.port.out;

import com.gaebaljip.exceed.member.domain.MemberModel;
import org.springframework.stereotype.Component;

@Component
public interface LoadMemberPort {

    public MemberModel loadMember(Long memberId);
}
