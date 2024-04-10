package com.gaebaljip.exceed.member.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.annotation.AuthenticationMemberId;
import com.gaebaljip.exceed.dto.request.UpdateMemberRequest;
import com.gaebaljip.exceed.member.application.port.in.UpdateMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.UpdateMemberUsecase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
@Tag(name = "[회원수정]")
public class UpdateMemberController {
    private final UpdateMemberUsecase updateMemberUsecase;

    @Operation(summary = "회원 수정", description = "회원정보를 수정한다.")
    @PutMapping("/members/")
//    @ApiErrorExceptionsExample(SignUpMemberExceptionDocs.class)
    public ApiResponse<ApiResponse.CustomBody<Void>> updateMember(
            @RequestBody @Valid UpdateMemberRequest updateMemberRequest, @AuthenticationMemberId Long memberId) {
        UpdateMemberCommand command = UpdateMemberCommand.of(memberId, updateMemberRequest);
        updateMemberUsecase.execute(updateMemberRequest, email);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
