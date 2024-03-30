package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;
import java.util.concurrent.CompletionException;

@Getter
public class MailCompletionException extends CompletionException {

    private final MessageCode messageCode;
    public MailCompletionException() {
        super(MessageCode.MAIL_SEND_FAIL.getValue());
        this.messageCode = MessageCode.MAIL_SEND_FAIL;
    }
}
