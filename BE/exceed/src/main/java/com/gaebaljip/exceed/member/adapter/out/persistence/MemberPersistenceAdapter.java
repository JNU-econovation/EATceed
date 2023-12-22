package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.member.application.port.out.LoadMemberPort;
import com.gaebaljip.exceed.member.application.port.out.RecordMemberPort;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements LoadMemberPort, RecordMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity query(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public void query(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
    }
}