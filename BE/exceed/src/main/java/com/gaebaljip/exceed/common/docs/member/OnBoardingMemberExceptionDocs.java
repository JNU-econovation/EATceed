package com.gaebaljip.exceed.common.docs.member;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.member.*;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class OnBoardingMemberExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("나이가 음수일 때")
    public EatCeedException 나이가_음수일_때 = InvalidAgeException.EXECPTION;

    @ExplainError("성별이 0과 1이 아닐 때")
    public EatCeedException 성별이_0과_1이_아닐_때 = InvalidGenderException.EXECPTION;

    @ExplainError("키가 음수일 때")
    public EatCeedException 키가_음수일_때 = InvalidHeightException.EXECPTION;

    @ExplainError("몸무게가 음수일 때")
    public EatCeedException 몸무게가_음수일_때 = InvalidWeightException.EXECPTION;

    @ExplainError("회원이 존재하지 않을 때")
    public EatCeedException 회원이_없을_때 = MemberNotFoundException.EXECPTION;
}
