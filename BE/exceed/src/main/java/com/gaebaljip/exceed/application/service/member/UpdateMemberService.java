package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.application.domain.member.Activity;
import com.gaebaljip.exceed.application.domain.member.Gender;
import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.member.UpdateMemberCommand;
import com.gaebaljip.exceed.application.port.in.member.UpdateMemberUsecase;
import com.gaebaljip.exceed.application.port.out.member.HistoryPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateMemberService implements UpdateMemberUsecase {
    private final MemberPort memberPort;
    private final HistoryPort historyPort;

    @Override
    @Transactional
    public void execute(UpdateMemberCommand command) {
        MemberEntity member = memberPort.query(command.memberId());
        HistoryEntity history =
                HistoryEntity.builder()
                        .height(member.getHeight())
                        .gender(member.getGender())
                        .weight(member.getWeight())
                        .targetWeight(member.getTargetWeight())
                        .age(member.getAge())
                        .activity(member.getActivity())
                        .memberEntity(member)
                        .build();
        historyPort.command(history);
        member.updateMember(
                command.height(),
                Gender.valueOf(command.gender()),
                command.age(),
                Activity.valueOf(command.activity()),
                command.etc(),
                member.getWeight(),
                member.getTargetWeight());
    }
}
