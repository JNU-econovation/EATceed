package com.gaebaljip.exceed.adapter.in.food.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;

import lombok.Builder;

public record CreateFoodRequest(
        @NotBlank(message = "이름을 " + ValidationMessage.NOT_BLANK) String name,
        @NotNull(message = "당을 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "당은" + ValidationMessage.MIN_0)
                Double sugars,
        @NotNull(message = "식이섬유를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "식이섬유는 " + ValidationMessage.MIN_0)
                Double dietaryFiber,
        @NotNull(message = "나트륨을 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "나트륨은 " + ValidationMessage.MIN_0)
                Double sodium,
        @NotNull(message = "칼로리를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "칼로리는 " + ValidationMessage.MIN_0)
                Double calorie,
        @NotNull(message = "탄수화물을 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "탄수화물은 " + ValidationMessage.MIN_0)
                Double carbohydrate,
        @NotNull(message = "단백질을 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "단백질은 " + ValidationMessage.MIN_0)
                Double protein,
        @NotNull(message = "지방을 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "지방은 " + ValidationMessage.MIN_0)
                Double fat,
        @NotNull(message = "1회 제공량을 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "1회 제공량은 " + ValidationMessage.MIN_0)
                Double servingSize) {
    @Builder
    public CreateFoodRequest {}
}
