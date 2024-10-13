package com.gaebaljip.exceed.common.exception.nutritionist;

import java.lang.reflect.Field;
import java.util.Objects;

import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.common.exception.BaseError;
import com.gaebaljip.exceed.common.swagger.ExplainError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NutritionistError implements BaseError {
    INVALID_MEAL(400, "NUTRITIONIST_400_1", "존재하지 않는 식사입니다."),
    NOT_SAME_YEAR_MONTH(500, "NUTRITIONIST_500_1", "모든 날짜의 연도와 월은 같아야합니다."),
    NOT_SAME_SIZE(500, "NUTRITIONIST_500_2", "해당 월의 일수와 size가 같아야합니다."),
    NOT_REQUIRED_MINIMUM_MEMBER(500, "NUTRITIONIST_500_3", "회원이 1개 이상 있어야합니다.");

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
