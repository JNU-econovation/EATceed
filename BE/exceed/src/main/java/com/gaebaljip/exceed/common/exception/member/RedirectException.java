package com.gaebaljip.exceed.common.exception.member;

import lombok.Getter;

@Getter
public class RedirectException extends RuntimeException {
    public RedirectException() {
        super("만료된 링크입니다.");
    }
}
