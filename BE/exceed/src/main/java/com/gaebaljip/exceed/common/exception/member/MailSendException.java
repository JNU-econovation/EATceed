package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class MailSendException extends EatCeedException {

    public static EatCeedException EXECPTION = new MailSendException();

    private MailSendException() {
        super(MemberError.MAIL_SEND_FAIL);
    }
}
