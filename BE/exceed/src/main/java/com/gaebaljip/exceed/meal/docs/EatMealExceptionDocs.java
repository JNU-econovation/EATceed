package com.gaebaljip.exceed.meal.docs;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;
import com.gaebaljip.exceed.food.exception.FoodNotFoundException;
import com.gaebaljip.exceed.meal.exception.ExtentionNotAllowedException;
import com.gaebaljip.exceed.meal.exception.InvalidMultipleException;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;
import com.gaebaljip.exceed.nutritionist.exception.MealNotFoundException;

@ExceptionDoc
public class EatMealExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("이미지의 확장자가 옳바르지 않을 때 ")
    public EatCeedException 이미지의_확장자가_옳바르지_않습니다 = ExtentionNotAllowedException.EXECPTION;

    @ExplainError("0인분 이하거나 100인분 초과일 경우 ")
    public EatCeedException _0인분_이하거나_100인분_초과일_경우 = InvalidMultipleException.EXECPTION;

    @ExplainError("Food를 찾지 못했을 때")
    public EatCeedException Food를_찾지_못했을_경우 = FoodNotFoundException.Exception;

    @ExplainError("Meal를 찾지 못했을 때")
    public EatCeedException Meal를_찾지_못했을_경우 = MealNotFoundException.EXECPTION;

    @ExplainError("회원이 존재하지 않을 때")
    public EatCeedException 회원이_없을_때 = MemberNotFoundException.EXECPTION;
}
