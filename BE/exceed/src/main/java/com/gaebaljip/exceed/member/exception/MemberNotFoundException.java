package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class MemberNotFoundException extends EatCeedException {
    public static EatCeedException EXECPTION = new MemberNotFoundException();

    private MemberNotFoundException() {
        super(MemberErrorCode.INVALID_MEMBER);
    }

}
