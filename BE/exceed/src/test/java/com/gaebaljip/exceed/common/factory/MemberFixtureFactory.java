package com.gaebaljip.exceed.common.factory;

import static org.jeasy.random.FieldPredicates.named;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import com.gaebaljip.exceed.application.domain.member.Member;

public class MemberFixtureFactory {
    public static Member create(int gender) {
        EasyRandomParameters parameters =
                new EasyRandomParameters()
                        .randomize(
                                Double.class,
                                () -> Math.random() * 100 + 1) // double 타입 랜덤 값 생성 (1~100 사이의 값)
                        .randomize(
                                Integer.class,
                                () ->
                                        (int)
                                                (Math.random() * 100
                                                        + 1)) // int 타입 랜덤 값 생성 (1~100 사이의 값)
                        .excludeField(named("gender")); // gender 필드는 제외

        EasyRandom easyRandom = new EasyRandom(parameters);
        return easyRandom.nextObject(Member.class).toBuilder().gender(gender).build();
    }
}
