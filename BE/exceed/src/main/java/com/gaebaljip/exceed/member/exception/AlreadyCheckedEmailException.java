package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class AlreadyCheckedEmailException extends EatCeedException {

    public static EatCeedException Exception = new AlreadyCheckedEmailException();

    private AlreadyCheckedEmailException() {
        super(MemberError.ALREADY_Checked_EMAIL);
    }
}
