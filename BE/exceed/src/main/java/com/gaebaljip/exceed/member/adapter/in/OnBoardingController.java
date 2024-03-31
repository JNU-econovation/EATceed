package com.gaebaljip.exceed.member.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.request.OnBoardingMemberRequest;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberUsecase;
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
public class OnBoardingController {

    private final OnBoardingMemberUsecase onBoardingMemberUsecase;

    @PostMapping("/members/detail")
    public ApiResponse<CustomBody<Void>> onBoardingMember(@RequestBody @Valid OnBoardingMemberRequest request, @AuthenticationMemberId Long memberId) {
        OnBoardingMemberCommand command = OnBoardingMemberCommand.of(memberId, request);
        onBoardingMemberUsecase.execute(command);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }
}
