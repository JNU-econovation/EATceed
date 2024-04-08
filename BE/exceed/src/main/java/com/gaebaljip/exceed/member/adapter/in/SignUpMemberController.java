package com.gaebaljip.exceed.member.adapter.in;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.request.CheckMemberRequest;
import com.gaebaljip.exceed.dto.request.SignUpMemberRequest;
import com.gaebaljip.exceed.member.application.port.in.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SignUpMemberController {

    private final ValidateEmailUsecase validateEmailUsecase;
    private final SendEmailUsecase sendEmailUsecase;
    private final CreateMemberUsecase createMemberUsecase;
    private final CheckCodeUsecase checkCodeUsecase;

    @PostMapping("/members")
    public ApiResponse<CustomBody<Void>> signUpMember(
            @RequestBody @Valid SignUpMemberRequest signUpMemberRequest) {
        validateEmailUsecase.execute(new ValidateEmailCommand(signUpMemberRequest.email()));
        sendEmailUsecase.execute(new SendEmailCommand(signUpMemberRequest.email()));
        createMemberUsecase.execute(signUpMemberRequest);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/members/checked")
    public ApiResponse<CustomBody<Void>> checkMember(
            @ModelAttribute @Valid CheckMemberRequest checkMemberRequest) {
        checkCodeUsecase.execute(checkMemberRequest);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
