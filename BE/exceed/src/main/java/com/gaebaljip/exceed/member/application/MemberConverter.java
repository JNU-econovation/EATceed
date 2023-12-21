package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.domain.MemberModel;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
    public MemberEntity toEntity(MemberModel memberModel, String etc) {
        return MemberEntity.builder()
                .identification("test")
                .weight(memberModel.getWeight())
                .height(memberModel.getHeight())
                .age(memberModel.getAge())
                .gender(memberModel.getGender())
                .etc(etc)
                .activity(memberModel.getActivity())
                .build();
    }

    public MemberModel toModel(MemberEntity memberEntity) {
        return null;
    }
}
