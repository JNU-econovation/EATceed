package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.application.port.out.RecordMemberPort;
import com.gaebaljip.exceed.member.domain.MemberModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements LoadMemberPort, RecordMemberPort {

    @Override
    public MemberModel query(Long memberId) {
        return null;
    }

    @Override
    public MemberModel query() {
        return null;
    }
}
