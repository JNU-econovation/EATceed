package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.dto.response.UpdateWeightResponse;
import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.UpdateWeightCommand;
import com.gaebaljip.exceed.member.application.port.in.UpdateWeightUsecase;
import com.gaebaljip.exceed.member.application.port.out.HistoryPort;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;

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
                        .age(memberEntity.getAge())
                        .activity(memberEntity.getActivity())
                        .memberEntity(memberEntity)
                        .build();
        historyPort.command(history);
        memberEntity.updateWeight(command.weight(), command.targetWeight());
        return UpdateWeightResponse.of(memberEntity.getWeight(), memberEntity.getTargetWeight());
    }
}
