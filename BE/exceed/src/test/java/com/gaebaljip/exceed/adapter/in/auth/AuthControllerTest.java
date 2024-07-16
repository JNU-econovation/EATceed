package com.gaebaljip.exceed.adapter.in.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.application.service.auth.AuthService;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.dto.request.LoginRequest;

@WebMvcTest(AuthController.class)
public class AuthControllerTest extends ControllerTest {
    @MockBean private AuthService authService;

    @Test()
    @DisplayName("로그인 실패 - 비밀번호 형식 안 맞을 때")
    void login_fail() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abcd1111!@gmail.com", "abcd");

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/auth/login")
                                .content(om.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test()
    @DisplayName("로그인 실패 - 이메일 형식 안 맞을 때")
    void login_fail2() throws Exception {
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
}
