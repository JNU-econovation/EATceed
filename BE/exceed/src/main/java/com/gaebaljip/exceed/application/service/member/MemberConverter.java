package com.gaebaljip.exceed.application.service.member;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.application.domain.member.HistoryEntity;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberConverter {
    public Member toModel(MemberEntity memberEntity) {
        return Member.builder()
                .height(memberEntity.getHeight())
                .weight(memberEntity.getWeight())
                .targetWeight(memberEntity.getTargetWeight())
                .gender(memberEntity.getGender().getValue())
                .activity(memberEntity.getActivity())
                .age(memberEntity.getAge())
                .build();
    }

    public Member toModel(HistoryEntity historyEntity) {
        return Member.builder()
                .height(historyEntity.getHeight())
                .weight(historyEntity.getWeight())
                .targetWeight(historyEntity.getTargetWeight())
                .gender(historyEntity.getGender().getValue())
                .activity(historyEntity.getActivity())
                .age(historyEntity.getAge())
                .build();
    }
}
