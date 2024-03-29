package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;
import java.util.concurrent.CancellationException;


@Getter
public class MailCancelledException extends CancellationException {

    private final MessageCode messageCode;

    public MailCancelledException() {
        super(MessageCode.MAIL_SEND_FAIL.getValue());
        this.messageCode = MessageCode.MAIL_SEND_FAIL;
    }
}