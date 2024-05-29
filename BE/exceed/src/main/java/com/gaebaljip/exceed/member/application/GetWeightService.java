package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.dto.response.GetWeightResponse;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.GetWeightUseCase;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
