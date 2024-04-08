package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class MailCancelledException extends EatCeedException {

    public static EatCeedException EXECPTION = new MailCancelledException();

    private MailCancelledException() {
        super(MemberErrorCode.MAIL_SEND_FAIL);
    }
}
