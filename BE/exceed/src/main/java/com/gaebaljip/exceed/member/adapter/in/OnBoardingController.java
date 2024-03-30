package com.gaebaljip.exceed.member.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.request.OnBoardingMemberRequest;
import com.gaebaljip.exceed.dto.response.OnBoardingMember;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberUsecase;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.security.AuthConstants;
import com.gaebaljip.exceed.security.domain.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OnBoardingController {

    private final OnBoardingMemberUsecase onBoardingMemberUsecase;
    private final JwtManager jwtManager;

    @PostMapping("/members/detail")
    public ApiResponse<CustomBody<OnBoardingMember>> onBoardingMember(@RequestBody @Valid OnBoardingMemberRequest request, HttpServletResponse response) {
        OnBoardingMemberCommand command = OnBoardingMemberCommand.builder()
                .height(request.height())
                .weight(request.weight())
                .gender(request.gender())
                .etc(request.etc())
                .age(request.age())
                .activity(Activity.valueOf(request.activity())).build();
        OnBoardingMember onBoardingMember = onBoardingMemberUsecase.execute(command);
        response.addHeader(AuthConstants.AUTH_HEADER.getValue(), jwtManager.generateAccessToken(onBoardingMember.memberId()));
        return ApiResponseGenerator.success(onBoardingMember, HttpStatus.CREATED);
    }
}
