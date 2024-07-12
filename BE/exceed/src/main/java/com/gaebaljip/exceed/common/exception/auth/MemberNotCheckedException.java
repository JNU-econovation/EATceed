package com.gaebaljip.exceed.common.exception.auth;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class MemberNotCheckedException extends EatCeedException {

    public static EatCeedException EXECPTION = new MemberNotCheckedException();

    public MemberNotCheckedException() {
        super(AuthError.MEMBER_NOT_CHECKED);
    }
}
