package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import lombok.Getter;
import java.util.concurrent.CancellationException;


@Getter
public class MailCancelledException extends EatCeedException {

    public static EatCeedException EXECPTION = new MailCancelledException();

    private MailCancelledException() {
        super(MemberErrorCode.MAIL_SEND_FAIL);
    }
}