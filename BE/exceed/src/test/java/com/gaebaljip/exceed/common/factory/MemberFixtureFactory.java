package com.gaebaljip.exceed.common.factory;

import static org.jeasy.random.FieldPredicates.named;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import com.gaebaljip.exceed.application.domain.member.Member;

public class MemberFixtureFactory {
    public static Member create(int gender) {
        EasyRandomParameters parameters =
                new EasyRandomParameters()
                        .randomize(Double.class, () -> Math.random() * 50 + 40)
                        .randomize(Integer.class, () -> (int) (Math.random() * 10 + 10))
                        .excludeField(named("gender")); // gender 필드는 제외

        EasyRandom easyRandom = new EasyRandom(parameters);
        return easyRandom.nextObject(Member.class).toBuilder().gender(gender).build();
    }
}
