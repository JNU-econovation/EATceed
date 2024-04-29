package com.gaebaljip.exceed.member.application;

import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.member.adapter.out.persistence.HistoryEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberConverter {
    public Member toModel(MemberEntity memberEntity) {
        return Member.builder()
                .height(memberEntity.getHeight())
                .weight(memberEntity.getWeight())
                .gender(memberEntity.getGender().getValue())
                .activity(memberEntity.getActivity())
                .age(memberEntity.getAge())
                .build();
    }

    public Member toModel(HistoryEntity historyEntity) {
        return Member.builder()
                .height(historyEntity.getHeight())
                .weight(historyEntity.getWeight())
                .gender(historyEntity.getGender().getValue())
                .activity(historyEntity.getActivity())
                .age(historyEntity.getAge())
                .build();
    }
}
