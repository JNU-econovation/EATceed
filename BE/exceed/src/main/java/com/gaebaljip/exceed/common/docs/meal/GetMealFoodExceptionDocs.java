package com.gaebaljip.exceed.common.docs.meal;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.meal.InValidDateFoundException;
import com.gaebaljip.exceed.common.exception.meal.NotSameDateException;
import com.gaebaljip.exceed.common.exception.member.InvalidGenderException;
import com.gaebaljip.exceed.common.exception.member.MemberNotFoundException;
import com.gaebaljip.exceed.common.exception.member.NotFoundHistoryException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class GetMealFoodExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("식사들의 날짜가 다를 경우")
    public EatCeedException 식사들의_날짜가_다를_경우 = NotSameDateException.EXECPTION;

    @ExplainError("성별이 0과 1이 아닐 때")
    public EatCeedException 성별이_0과_1이_아닐_때 = InvalidGenderException.EXECPTION;

    @ExplainError("회원이 존재하지 않을 때")
    public EatCeedException 회원이_없을_때 = MemberNotFoundException.EXECPTION;

    @ExplainError("켈린더 상세 조회시 회원가입 전의 기록을 보려고 할 때")
    public EatCeedException 회원가입_전의_기록_열람시 = InValidDateFoundException.EXECPTION;

    @ExplainError("켈린더 상세 조회시 해당 날짜에 회원에대한 기록이 없을 경우")
    public EatCeedException 회원에대한_기록이_없을_경우 = NotFoundHistoryException.EXECPTION;
}
