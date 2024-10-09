package com.gaebaljip.exceed.adapter.in.auth;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.adapter.in.auth.request.LoginRequest;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.dto.TokenDTO;

public class AuthControllerTest extends ControllerTest {

    @Test()
    @DisplayName("로그인 실패 - 비밀번호 형식 안 맞을 때")
    void when_login_expected_fail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abcd1111!@gmail.com", "abcd");

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/auth/login")
                                .content(om.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value(ValidationMessage.INVALID_PASSWORD));
    }

    @Test()
    @DisplayName("로그인 실패 - 이메일 형식 안 맞을 때")
    void when_login_expected_fail2() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abcd1111gmail.com", "Abc@123");

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/auth/login")
                                .content(om.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value(ValidationMessage.INVALID_EMAIL));
    }

    @Test()
    @DisplayName(
            "로그인 성공" + "Authoriztion 헤더에 accessToken이 존재" + "refreshToken 쿠키에 refreshToken이 존재")
    void when_login_expected_success() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abcd1111!@gmail.com", "Abc@123");
        TokenDTO tokenDTO = new TokenDTO("accessToken", "refreshToken");
        given(authService.login(loginRequest)).willReturn(tokenDTO);

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/auth/login")
                                .content(om.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isOk(),
                header().exists("Authorization"),
                cookie().value("refreshToken", tokenDTO.refreshToken()));
    }
}
