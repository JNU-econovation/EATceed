package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.adapter.in.member.response.UpdateWeightResponse;
import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.UpdateWeightCommand;
import com.gaebaljip.exceed.application.port.in.member.UpdateWeightUsecase;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateWeightService implements UpdateWeightUsecase {
    private final MemberPort memberPort;
    private final HistoryPort historyPort;

    @Override
    @Transactional
    public UpdateWeightResponse execute(UpdateWeightCommand command) {
        MemberEntity memberEntity = memberPort.query(command.memberId());
        HistoryEntity history =
                HistoryEntity.builder()
                        .height(memberEntity.getHeight())
                        .gender(memberEntity.getGender())
                        .weight(memberEntity.getWeight())
                        .targetWeight(memberEntity.getTargetWeight())
                        .age(memberEntity.getAge())
                        .activity(memberEntity.getActivity())
                        .memberEntity(memberEntity)
                        .build();
        historyPort.command(history);
        memberEntity.updateWeight(command.weight(), command.targetWeight());
        return UpdateWeightResponse.of(memberEntity.getWeight(), memberEntity.getTargetWeight());
    }
}
