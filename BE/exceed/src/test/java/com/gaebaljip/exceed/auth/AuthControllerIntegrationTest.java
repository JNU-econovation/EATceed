package com.gaebaljip.exceed.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.dto.request.LoginRequest;

class AuthControllerIntegrationTest extends IntegrationTest {

    @Test()
    @DisplayName("로그인 성공")
    void login() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abcd123!@gmail.com", "Abc@123");

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/auth/login")
                                .content(om.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(header().exists("Authorization"));
        resultActions.andExpect(cookie().exists("refreshToken"));
    }

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
