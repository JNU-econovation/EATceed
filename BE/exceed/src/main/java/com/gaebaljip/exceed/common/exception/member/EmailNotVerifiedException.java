package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class EmailNotVerifiedException extends EatCeedException {

    public static EatCeedException EXECPTION = new EmailNotVerifiedException();

    public EmailNotVerifiedException() {
        super(MemberError.ALREADY_EMAIL);
    }
}
