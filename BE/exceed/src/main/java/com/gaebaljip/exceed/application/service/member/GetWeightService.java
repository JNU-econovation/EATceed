package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.member.response.GetWeightDTO;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.GetWeightUseCase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetWeightService implements GetWeightUseCase {
    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    @Override
    @Transactional(readOnly = true)
    public GetWeightDTO execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Member member = memberConverter.toModel(memberEntity);
        return GetWeightDTO.of(member.getWeight(), member.getTargetWeight());
    }
}
