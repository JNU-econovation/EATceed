package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class MemberNotFoundException extends NoSuchElementException {

    private final MessageCode messageCode;

    public MemberNotFoundException() {
        super(MessageCode.INVALID_MEMBER.getValue());
        this.messageCode = MessageCode.INVALID_MEMBER;
    }

}
