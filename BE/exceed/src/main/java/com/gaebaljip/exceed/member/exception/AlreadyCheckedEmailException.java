package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class AlreadyCheckedEmailException extends IllegalStateException {

    private final MessageCode messageCode;

    public AlreadyCheckedEmailException() {
        super(MessageCode.ALREADY_Checked_EMAIL.getValue());
        this.messageCode = MessageCode.ALREADY_Checked_EMAIL;
    }
}
