package com.gaebaljip.exceed.common.exception.food;

import java.lang.reflect.Field;
import java.util.Objects;

import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.common.exception.BaseError;
import com.gaebaljip.exceed.common.swagger.ExplainError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodError implements BaseError {
    INVALID_FOOD(400, "FOOD_400_1", "존재하지 않는 음식입니다."),
    CANNOT_DELETE_OTHERS_FOOD(400, "FOOD_400_2", "다른 사람의 음식은 삭제할 수 없습니다."),
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
