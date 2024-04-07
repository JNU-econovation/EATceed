package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import lombok.Getter;
import java.util.concurrent.CompletionException;

@Getter
public class MailCompletionException extends EatCeedException {
    public static EatCeedException EXECPTION = new MailCompletionException();
    private MailCompletionException() {
        super(MemberErrorCode.MAIL_SEND_FAIL);
    }
}
