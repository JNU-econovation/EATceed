package com.gaebaljip.exceed.member.adapter.in;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponse.CustomBody;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.dto.request.CreateGuestRequest;
import com.gaebaljip.exceed.dto.response.CreateGuest;
import com.gaebaljip.exceed.member.application.port.in.CreateMemberCommand;
import com.gaebaljip.exceed.member.application.port.in.CreateGuestUsecase;
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
public class CreateGuestController {

    private final CreateGuestUsecase createGuestUsecase;
    private final JwtManager jwtManager;

    @PostMapping("/members-guest")
    public ApiResponse<CustomBody<CreateGuest>> createGuest(@Valid @RequestBody CreateGuestRequest request, HttpServletResponse response) {
        CreateMemberCommand command = CreateMemberCommand.builder()
                .height(request.height())
                .weight(request.weight())
                .gender(request.gender())
                .etc(request.etc())
                .age(request.age())
                .activity(Activity.valueOf(request.activity())).build();
        CreateGuest createGuest = createGuestUsecase.execute(command);
        response.addHeader(AuthConstants.AUTH_HEADER.getValue(), jwtManager.generateAccessToken(createGuest.loginId(), createGuest.memberId()));
        return ApiResponseGenerator.success(createGuest, HttpStatus.CREATED);
    }
}
