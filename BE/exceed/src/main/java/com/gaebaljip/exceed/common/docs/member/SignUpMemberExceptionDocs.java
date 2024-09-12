package com.gaebaljip.exceed.common.docs.member;

import com.gaebaljip.exceed.common.exception.DecryptionErrorException;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.EncryptionErrorException;
import com.gaebaljip.exceed.common.exception.member.AlreadySignUpMemberException;
import com.gaebaljip.exceed.common.exception.member.EmailNotVerifiedException;
import com.gaebaljip.exceed.common.exception.member.MailSendException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class SignUpMemberExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("이미 회원가입된 회원일 때")
    public EatCeedException 이미_회원가입된_회원일_때 = AlreadySignUpMemberException.EXECPTION;

    @ExplainError("이메일 인증을 하지 않았을 때")
    public EatCeedException 이메일_인증을_하지_않았을_때 = EmailNotVerifiedException.EXECPTION;

    @ExplainError("암호화 실패 시")
    public EatCeedException 암호화_실패_시 = EncryptionErrorException.EXECPTION;

    @ExplainError("복호화 실패 시")
    public EatCeedException 복호화_실패_시 = DecryptionErrorException.EXECPTION;

    @ExplainError("메일 전송 실패 시")
    public EatCeedException 메일_전송_실패_시 = MailSendException.EXECPTION;
}
