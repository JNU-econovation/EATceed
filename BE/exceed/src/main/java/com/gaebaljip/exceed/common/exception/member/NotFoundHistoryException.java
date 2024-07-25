package com.gaebaljip.exceed.common.exception.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class NotFoundHistoryException extends EatCeedException {

    public static EatCeedException EXECPTION = new NotFoundHistoryException();

    public NotFoundHistoryException() {
        super(MemberError.HISTORY_NOT_FOUND);
    }
}
