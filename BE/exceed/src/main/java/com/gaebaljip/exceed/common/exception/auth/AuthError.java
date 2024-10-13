package com.gaebaljip.exceed.common.exception.auth;

import java.lang.reflect.Field;
import java.util.Objects;

import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.common.exception.BaseError;
import com.gaebaljip.exceed.common.swagger.ExplainError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthError implements BaseError {
    PASSWORD_MISMATCH(400, "AUTH_400_1", "비밀번호가 일치하지 않습니다."),
    MEMBER_NOT_CHECKED(400, "AUTH_400_2", "해당 회원은 이메일 검증이 완료되지 않았습니다."),
    NOT_FOUND_REFRESHTOKEN(400, "AUTH_400_3", "리프레시 토큰이 존재하지 않습니다.");

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
