package com.gaebaljip.exceed.member.adapter.in;

import com.gaebaljip.exceed.common.CommonApiTest;
import com.gaebaljip.exceed.member.application.port.in.CreateGuestUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreateGuestController.class)
class CreateGuestControllerTest extends CommonApiTest {

    @MockBean
    private CreateGuestUsecase createGuestUsecase;

    @Test
    @DisplayName("회원가입 성공")
    void createGuest() throws Exception {
        //given

        CreateGuestTestRequest request = new CreateGuestTestRequest(
                171, 1, 61, 25, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/v1/members")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));


        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("유효하지 않은 activity 입력시 오류 발생")
    void createGuest_invalidActivity() throws Exception {
        //given

        String invalidValue = "ACTIVE";

        CreateGuestTestRequest request = new CreateGuestTestRequest(
                171, 1, 61, 25, invalidValue, "뭐든 잘 먹습니다.");

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/v1/members")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));


        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.error.reason").value(invalidValue + "는 올바르지 않은 값입니다."));
    }


}