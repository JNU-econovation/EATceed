package com.gaebaljip.exceed.common.docs.meal;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleException;
import com.gaebaljip.exceed.common.exception.meal.NotSameDateException;
import com.gaebaljip.exceed.common.exception.member.InvalidGenderException;
import com.gaebaljip.exceed.common.exception.member.MemberNotFoundException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class GetMealExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("0인분 이하거나 100인분 초과일 경우 ")
    public EatCeedException _0인분_이하거나_100인분_초과일_경우 = InvalidMultipleException.EXECPTION;

    @ExplainError("식사들의 날짜가 다를 경우")
    public EatCeedException 식사들의_날짜가_다를_경우 = NotSameDateException.EXECPTION;

    @ExplainError("성별이 0과 1이 아닐 때")
    public EatCeedException 성별이_0과_1이_아닐_때 = InvalidGenderException.EXECPTION;

    @ExplainError("회원이 존재하지 않을 때")
    public EatCeedException 회원이_없을_때 = MemberNotFoundException.EXECPTION;
}
