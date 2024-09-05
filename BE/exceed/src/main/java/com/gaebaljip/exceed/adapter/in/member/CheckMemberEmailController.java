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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[회원가입]")
public class CheckMemberEmailController {

    private final CheckCodeUsecase checkCodeUsecase;
    private final GetCodeUsecase getCodeUsecase;

    @Value("${exceed.deepLink}")
    private String deepLink;

    @GetMapping("/members/checked")
    public ApiResponse<ApiResponse.CustomBody<Void>> checkMemberEmail(
            @ModelAttribute @Valid CheckMemberRequest checkMemberRequest) {
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
