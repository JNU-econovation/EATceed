package com.gaebaljip.exceed.adapter.in.member;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.gaebaljip.exceed.application.port.in.member.GetCodeUsecase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gaebaljip.exceed.adapter.in.member.request.SendEmailRequest;
import com.gaebaljip.exceed.application.port.in.member.PasswordValidationUsecase;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "[비밀번호 변경]")
public class UpdatePasswordController {

    private final PasswordValidationUsecase passwordValidationUsecase;
    private final GetCodeUsecase getCodeUsecase;
    @Value("${exceed.deepLink.updatePassword}")
    private String deepLink;
    @Operation(
            summary = "비밀번호 변경 전 이메일 검증 및 메일 전송",
            description = "비밀번호 변경하기 전, 이메일 검증 및 이메일을 재전송한다.")
    @PostMapping("/email")
    public ApiResponse<CustomBody<Void>> validateEmail(
            @RequestBody @Valid SendEmailRequest request) {
        passwordValidationUsecase.execute(request.email());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
    @Operation(summary = "링크 클릭시 리다이렉트", description = "AOS는 몰라도 되는 API")
    @GetMapping("/updatePassword-redirect")
    public void redirect(@RequestParam String email, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        String code = getCodeUsecase.execute(email);
        String redirectUrl = sb.append(deepLink).append("?code=").append(code).toString();
        response.setHeader("Location", redirectUrl);
        response.setStatus(302);
    }
}

