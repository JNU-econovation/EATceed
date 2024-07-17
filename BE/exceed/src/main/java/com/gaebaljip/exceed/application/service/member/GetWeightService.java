package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;

import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.GetWeightUseCase;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.response.GetWeightResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetWeightService implements GetWeightUseCase {
    private final MemberPort memberPort;
    private final MemberConverter memberConverter;

    @Override
    public GetWeightResponse execute(Long memberId) {
        MemberEntity memberEntity = memberPort.query(memberId);
        Member member = memberConverter.toModel(memberEntity);
        return GetWeightResponse.of(member.getWeight(), member.getTargetWeight());
    }
}
