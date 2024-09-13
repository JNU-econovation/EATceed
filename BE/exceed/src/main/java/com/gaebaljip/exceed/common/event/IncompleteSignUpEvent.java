package com.gaebaljip.exceed.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncompleteSignUpEvent extends InfraEvent {
    private String email;
    private String password;

    public static IncompleteSignUpEvent from(String email, String password) {
        return new IncompleteSignUpEvent(email, password);
    }
}
