package com.gaebaljip.exceed.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendEmailEvent extends InfraEvent {
    private final String email;

    public static SendEmailEvent of(String email) {
        return new SendEmailEvent(email);
    }
}
