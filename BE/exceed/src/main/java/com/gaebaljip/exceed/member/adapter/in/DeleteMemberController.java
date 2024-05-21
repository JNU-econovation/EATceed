package com.gaebaljip.exceed.member.adapter.in;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.member.application.port.in.DeleteMemberUseCase;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[회원탈퇴]")
public class DeleteMemberController {
    private final DeleteMemberUseCase deleteMemberUseCase;

    @DeleteMapping("/members")
    public ApiResponse<ApiResponse.CustomBody<Void>> deleteMember(
            @Parameter(hidden = true) @AuthenticationMemberId Long memberId) {
        deleteMemberUseCase.execute(memberId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
