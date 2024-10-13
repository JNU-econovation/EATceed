package com.gaebaljip.exceed.common.exception;

import java.lang.reflect.Field;
import java.util.Objects;

import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.common.swagger.ExplainError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalError implements BaseError {
    ENCRYPTION_FAIL(500, "ENCRYPTION_500_1", "암호화에 실패하였습니다."),
    DECRYPTION_FAIL(500, "ENCRYPTION_500_2", "복호화에 실패하였습니다."),
    EXTENTION_NOT_ALLOWED(400, "GLOBAL_400_1", "이미지의 확장자는 jpg, jpeg, png만 가능합니다."),

    ;

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
