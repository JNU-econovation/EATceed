package com.gaebaljip.exceed.integration.auth;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.adapter.in.auth.request.LoginRequest;
import com.gaebaljip.exceed.common.IntegrationTest;

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
}
