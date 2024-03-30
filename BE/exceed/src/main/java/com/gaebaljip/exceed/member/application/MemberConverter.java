package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberConverter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberEntity toEntity(Member member, String etc) {
        return MemberEntity.builder()
                .weight(member.getWeight())
                .height(member.getHeight())
                .age(member.getAge())
                .gender(member.getGender())
                .etc(etc)
                .activity(member.getActivity())
                .build();
    }

    public Member toModel(MemberEntity memberEntity) {
        return Member.builder()
                .height(memberEntity.getHeight())
                .weight(memberEntity.getWeight())
                .gender(memberEntity.getGender())
                .activity(memberEntity.getActivity())
                .age(memberEntity.getAge())
                .build();
    }
}
