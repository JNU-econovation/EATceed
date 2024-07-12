package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class AlreadySignUpMemberException extends EatCeedException {

    public static EatCeedException EXECPTION = new AlreadySignUpMemberException();

    public AlreadySignUpMemberException() {
        super(MemberError.ALREADY_SIGN_UP_MEMBER);
    }
}
