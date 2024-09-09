package com.gaebaljip.exceed.common.docs.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.member.ExpiredCodeException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class CheckMemberEmailExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("인증 코드가 만료되었을 경우")
    public EatCeedException 인증코드가_만료되었을_경우 = ExpiredCodeException.EXECPTION;
}
