package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class AlreadyEmailException extends EatCeedException {

    public static EatCeedException EXECPTION = new AlreadyEmailException();

    public AlreadyEmailException() {
        super(MemberError.ALREADY_EMAIL);
    }
}
