package com.gaebaljip.exceed.common.exception.member;

import java.lang.reflect.Field;
import java.util.Objects;

import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.common.exception.BaseError;
import com.gaebaljip.exceed.common.swagger.ExplainError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberError implements BaseError {
    ALREADY_SIGN_UP_MEMBER(400, "4449", "이미 회원가입이 된 이메일입니다."),
    ALREADY_EMAIL(400, "4448", "이미 등록된 이메일입니다. 해당 이메일이 본인 소유임을 증명해주세요."),
    INVALID_AGE(400, "4446", "나이는 음수일 수 없습니다."),
    INVALID_CODE(400, "7003", "올바르지 않은 인증 코드이거나 만료된 인증코드입니다."),
    INVALID_GENDER(400, "4447", "성별은 1과 0으로만 표현됩니다."),
    INVALID_HEIGHT(400, "4444", "키는 음수일 수 없습니다."),
    INVALID_WEIGHT(400, "4445", "몸무게가 음수일 수 없습니다."),
    MAIL_SEND_FAIL(400, "7002", "메일 전송에 실패하였습니다."),
    INVALID_MEMBER(400, "4448", "존재하지 않는 회원입니다."),
    HISTORY_NOT_FOUND(500, "5555", "첫 온보딩하기 전입니다.");
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
