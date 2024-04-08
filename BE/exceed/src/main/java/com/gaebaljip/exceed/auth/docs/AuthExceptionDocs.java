package com.gaebaljip.exceed.auth.docs;

import com.gaebaljip.exceed.auth.exception.PasswordMismatchException;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class AuthExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("비밀번호가 일치하지 않을 때")
    public EatCeedException 비밀번호가_일치하지_않을_때 = PasswordMismatchException.EXECPTION;

    @ExplainError("해당 회원은 이메일 검증이 완료되지 않았습니다.")
    public EatCeedException 해당_회원은_이메일_검증이_완료되지_않았습니다 = PasswordMismatchException.EXECPTION;
}
