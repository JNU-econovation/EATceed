package com.gaebaljip.exceed.member.domain;

import lombok.*;

@Getter
@ToString
public class Guest extends Member {

    private String loginId;
    private String password;
    private MemberRole role;

    @Builder(builderMethodName = "guestBuilder")
    public Guest(double height, int gender, double weight, int age, Activity activity, String loginId, String password) {
        super(height, gender, weight, age, activity);
        this.loginId = loginId;
        this.password = password;
        this.role = MemberRole.GUEST;
    }

    public static Guest create(double height, int gender, double weight, int age, Activity activity) {
        return Guest.guestBuilder()
                .height(height)
                .gender(gender)
                .weight(weight)
                .age(age)
                .activity(activity)
                .loginId(new GuestIdGenerator().getGuestId())
                .password(new GuestIdGenerator().getGuestId())
                .build();
    }

}
