package com.gaebaljip.exceed.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendEmailEvent extends InfraEvent {

    private String email;

    public static SendEmailEvent from(String email) {
        return new SendEmailEvent(email);
    }
}
