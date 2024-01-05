package com.gaebaljip.exceed.member.application.port.out;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public interface LoadMemberPort {

    MemberEntity query(Long memberId);
}
