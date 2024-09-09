package com.gaebaljip.exceed.adapter.in.member;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gaebaljip.exceed.adapter.in.member.request.CheckMemberRequest;
import com.gaebaljip.exceed.application.port.in.member.CheckCodeUsecase;
import com.gaebaljip.exceed.application.port.in.member.GetCodeUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.docs.member.CheckMemberEmailExceptionDocs;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "[이메일 인증]")
public class CheckMemberEmailController {

    private final CheckCodeUsecase checkCodeUsecase;
    private final GetCodeUsecase getCodeUsecase;

    @Value("${exceed.deepLink}")
    private String deepLink;

    @Operation(summary = "이메일 코드 확인", description = "해당 이메일의 인증 코드인지 확인한다.(딥링크로 들어간 화면에서 사용)")
    @PatchMapping("/members/checked")
    @ApiErrorExceptionsExample(CheckMemberEmailExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<Void>> checkMemberEmail(
            @RequestBody @Valid CheckMemberRequest checkMemberRequest) {
        checkCodeUsecase.execute(checkMemberRequest);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/email/redirect")
    public void redirect(@RequestParam String email, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        String code = getCodeUsecase.execute(email);
        String redirectUrl = sb.append(deepLink).append("?code=").append(code).toString();
        response.setHeader("Location", redirectUrl);
        response.setStatus(302);
    }
}
