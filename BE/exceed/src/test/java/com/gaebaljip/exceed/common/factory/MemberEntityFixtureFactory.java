package com.gaebaljip.exceed.common.factory;

import com.gaebaljip.exceed.application.domain.member.MemberEntity;

public class MemberEntityFixtureFactory {

    public static MemberEntity create() {
        return MemberEntity.builder().id(111L).build();
    }
}
