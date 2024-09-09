package com.gaebaljip.exceed.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpMemberEvent extends DomainEvent {

    private String email;

    public static SignUpMemberEvent from(String email) {
        return new SignUpMemberEvent(email);
    }
}
