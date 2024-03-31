package com.gaebaljip.exceed.member.application;

import com.gaebaljip.exceed.common.BaseEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.WeightEntity;
import com.gaebaljip.exceed.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberConverter {
    public Member toModel(MemberEntity memberEntity) {
        List<WeightEntity> weightEntities = memberEntity.getWeightEntities();
        weightEntities.sort(Comparator.comparing(BaseEntity::getCreatedDate));
        return Member.builder()
                .height(memberEntity.getHeight())
                .weight(weightEntities.get(weightEntities.size()-1).getWeight())
                .gender(memberEntity.getGender().getValue())
                .activity(memberEntity.getActivity())
                .age(memberEntity.getAge())
                .build();
    }
}
