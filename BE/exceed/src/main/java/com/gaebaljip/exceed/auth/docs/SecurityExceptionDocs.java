package com.gaebaljip.exceed.auth.docs;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;
import com.gaebaljip.exceed.security.exception.ExpiredJwtAuthenticationException;
import com.gaebaljip.exceed.security.exception.InvalidJwtAuthenticationException;
import com.gaebaljip.exceed.security.exception.UnsupportedAuthenticationException;

@ExceptionDoc
public class SecurityExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("유효하지 않은 JWT 토큰")
    public EatCeedException 유효하지_않은_JWT_토큰 = InvalidJwtAuthenticationException.EXECPTION;

    @ExplainError("지원하지 않는 JWT 토큰")
    public EatCeedException 지원하지_않는_JWT_토큰 = UnsupportedAuthenticationException.EXECPTION;

    @ExplainError("만료된 토큰")
    public EatCeedException 만료된_토큰 = ExpiredJwtAuthenticationException.EXECPTION;
}
