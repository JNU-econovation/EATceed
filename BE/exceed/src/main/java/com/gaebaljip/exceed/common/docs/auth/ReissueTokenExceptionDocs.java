package com.gaebaljip.exceed.common.docs.auth;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.auth.NotFoundRefreshTokenException;
import com.gaebaljip.exceed.common.security.exception.SecurityErrorCode;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class ReissueTokenExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("리프레시 토큰이 존재 하지 않을 때")
    public EatCeedException 리프레시_토큰이_존재_하지_않을_때 = NotFoundRefreshTokenException.EXECPTION;

    @ExplainError("토큰이 유효 하지 않을 때 ")
    public EatCeedException 토큰이_유효_하지_않을_때 = new EatCeedException(SecurityErrorCode.INVALID_JWT);

    @ExplainError("리프레시 토큰이 만료 되었을 때")
    public EatCeedException 리프레시_토큰이_만료_되었을_때 = new EatCeedException(SecurityErrorCode.EXPIRED_JWT);

    @ExplainError("리프레시 토큰이 서버에서 지원하는 토큰이 아닐 떄")
    public EatCeedException 리프레시_토큰이_서버에서_지원하는_토큰이_아닐_때 =
            new EatCeedException(SecurityErrorCode.UNSUPPORTED_JWT);
}
