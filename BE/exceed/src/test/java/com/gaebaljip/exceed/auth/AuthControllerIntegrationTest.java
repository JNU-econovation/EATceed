package com.gaebaljip.exceed.auth;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerIntegrationTest extends IntegrationTest {

    @Test()
    void login() throws Exception {
        LoginRequest loginRequest = new LoginRequest("abcd1111!@gmail.com", "abcd1234@");

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/auth/login")
                        .content(om.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(header().exists("Authorization"));
        resultActions.andExpect(cookie().exists("refreshToken"));


    }
}