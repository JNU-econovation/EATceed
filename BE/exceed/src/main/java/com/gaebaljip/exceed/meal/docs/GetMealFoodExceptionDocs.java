package com.gaebaljip.exceed.meal.docs;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;
import com.gaebaljip.exceed.meal.exception.InsufficientMealsException;
import com.gaebaljip.exceed.meal.exception.NotSameDateException;
import com.gaebaljip.exceed.member.exception.InvalidGenderException;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;

@ExceptionDoc
public class GetMealFoodExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("Daily Meal에 최소 1끼도 제공 되지 않았을 때 ")
    public EatCeedException Daily_Meal에_최소_1끼도_제공_되지_않았습니다 = InsufficientMealsException.EXECPTION;

    @ExplainError("식사들의 날짜가 다를 경우")
    public EatCeedException 식사들의_날짜가_다를_경우 = NotSameDateException.EXECPTION;

    @ExplainError("성별이 0과 1이 아닐 때")
    public EatCeedException 성별이_0과_1이_아닐_때 = InvalidGenderException.EXECPTION;

    @ExplainError("회원이 존재하지 않을 때")
    public EatCeedException 회원이_없을_때 = MemberNotFoundException.EXECPTION;
}
