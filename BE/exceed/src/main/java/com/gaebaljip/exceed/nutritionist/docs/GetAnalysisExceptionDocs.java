package com.gaebaljip.exceed.nutritionist.docs;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;
import com.gaebaljip.exceed.meal.exception.InsufficientMealsException;
import com.gaebaljip.exceed.meal.exception.NotSameDateException;
import com.gaebaljip.exceed.member.exception.MemberNotFoundException;

@ExceptionDoc
public class GetAnalysisExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("Daily Meal에 최소 1끼도 제공 되지 않았을 때 ")
    public EatCeedException Daily_Meal에_최소_1끼도_제공_되지_않았습니다 = InsufficientMealsException.EXECPTION;

    @ExplainError("식사들의 날짜가 다를 경우")
    public EatCeedException 식사들의_날짜가_다를_경우 = NotSameDateException.EXECPTION;

    @ExplainError("회원이 존재하지 않을 때")
    public EatCeedException 회원이_없을_때 = MemberNotFoundException.EXECPTION;
}
