package com.gaebaljip.exceed.member.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.ApiErrorExceptionsExample;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.request.OnBoardingMemberRequest;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberUsecase;
import com.gaebaljip.exceed.member.docs.OnBoardingMemberExceptionDocs;
import com.gaebaljip.exceed.security.domain.JwtManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[온보딩]")
public class OnBoardingController {

    private final OnBoardingMemberUsecase onBoardingMemberUsecase;
    private final JwtManager jwtManager;

    @Operation(summary = "회원 정보 온보딩", description = "회원 정보를 온보딩한다.")
    @PostMapping("/members/detail")
    @ApiErrorExceptionsExample(OnBoardingMemberExceptionDocs.class)
    public ApiResponse<CustomBody<Void>> onBoardingMember(@RequestBody @Valid OnBoardingMemberRequest request, @AuthenticationMemberId Long memberId) {
        OnBoardingMemberCommand command = OnBoardingMemberCommand.of(memberId, request);
        onBoardingMemberUsecase.execute(command);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }
}
