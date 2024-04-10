package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.application.port.in.UpdateMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.UpdateMemberUsecase;
import com.gaebaljip.exceed.member.application.port.out.HistoryPort;
import com.gaebaljip.exceed.member.application.port.out.MemberPort;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.Gender;

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
                command.weight(),
                command.targetWeight());
    }
}
