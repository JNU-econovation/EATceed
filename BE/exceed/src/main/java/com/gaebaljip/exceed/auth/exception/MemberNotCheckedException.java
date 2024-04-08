package com.gaebaljip.exceed.auth.exception;

import com.gaebaljip.exceed.common.MessageCode;

import lombok.Getter;

@Getter
public class MemberNotCheckedException extends IllegalStateException {

    private final MessageCode messageCode;

    public MemberNotCheckedException() {
        super(MessageCode.MEMBER_NOT_CHECKED.getValue());
        this.messageCode = MessageCode.MEMBER_NOT_CHECKED;
    }
}
