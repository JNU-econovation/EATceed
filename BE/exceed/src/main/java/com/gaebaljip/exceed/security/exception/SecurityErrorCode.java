package com.gaebaljip.exceed.security.exception;

import com.gaebaljip.exceed.common.annotation.ExplainError;
import com.gaebaljip.exceed.common.exception.BaseErrorCode;
import com.gaebaljip.exceed.common.exception.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode {
    INVALID_JWT(401,"5000", "잘못된 토큰입니다."),
    EXPIRED_JWT(401,"5001", "만료된 토큰입니다."),
    UNSUPPORTED_JWT(401,"5002", "지원되지 않는 토큰입니다.");

    private final Integer status;
    private final String code;
    private final String reason;


//    @Override
//    public ErrorReason getErrorReason() {
//        return ErrorReason.builder().reason(reason).code(code).status(status).build();
//    }
//
//    @Override
//    public String getExplainError() throws NoSuchFieldException {
//        Field field = this.getClass().getField(this.name());
//        ExplainError annotation = field.getAnnotation(ExplainError.class);
//        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
//    }
}


