package com.gaebaljip.exceed.common.security.exception;

import java.lang.reflect.Field;
import java.util.Objects;

import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.common.exception.BaseError;
import com.gaebaljip.exceed.common.swagger.ExplainError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements BaseError {
    INVALID_JWT(401, "SECURITY_401_1", "잘못된 토큰입니다."),
    EXPIRED_JWT(401, "SECURITY_401_2", "만료된 토큰입니다."),
    UNSUPPORTED_JWT(401, "SECURITY_401_3", "지원되지 않는 토큰입니다."),
    SIGNATURE_JWT(401, "SECURITY_401_4", "토큰의 형식이 잘못 됬습니다."),
    NEED_AUTHENTICATION(401, "SECURITY_401_5", "인증이 필요합니다.");

    private final Integer status;
    private final String code;
    private final String reason;

    @Override
    public Error getError() {
        return Error.builder().reason(reason).code(code).status(status.toString()).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getReason();
    }
}
