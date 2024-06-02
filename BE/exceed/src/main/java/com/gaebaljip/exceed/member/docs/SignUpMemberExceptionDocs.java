package com.gaebaljip.exceed.member.docs;

import com.gaebaljip.exceed.common.exception.DecryptionErrorException;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.EncryptionErrorException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;
import com.gaebaljip.exceed.member.exception.AlreadyEmailException;
import com.gaebaljip.exceed.member.exception.AlreadySignUpMemberException;
import com.gaebaljip.exceed.member.exception.MailSendException;

@ExceptionDoc
public class SignUpMemberExceptionDocs implements SwaggerExampleExceptions {

    @ExplainError("이미 회원가입된 회원일 때")
    public EatCeedException 이미_회원가입된_회원일_때 = AlreadySignUpMemberException.EXECPTION;

    @ExplainError("이미 가입된 이메일이 있습니다. 이메일이 본인 소유인지 증명해주세요.")
    public EatCeedException 이미_가입된_이메일_때 = AlreadyEmailException.EXECPTION;

    @ExplainError("암호화 실패 시")
    public EatCeedException 암호화_실패_시 = EncryptionErrorException.EXECPTION;

    @ExplainError("복호화 실패 시")
    public EatCeedException 복호화_실패_시 = DecryptionErrorException.EXECPTION;

    @ExplainError("메일 전송 실패 시")
    public EatCeedException 메일_전송_실패_시 = MailSendException.EXECPTION;
}
