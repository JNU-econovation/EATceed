package com.gaebaljip.exceed.member.adapter.in;

import com.gaebaljip.exceed.common.CommonApiTest;
import com.gaebaljip.exceed.dto.response.OnBoardingMember;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberUsecase;
import com.gaebaljip.exceed.member.application.port.in.OnBoardingMemberCommand;
import com.gaebaljip.exceed.security.domain.JwtManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OnBoardingController.class)
class OnBoardingControllerTest extends CommonApiTest {

    @MockBean
    private OnBoardingMemberUsecase onBoardingMemberUsecase;
    @MockBean
    private JwtManager jwtManager;

    @Test
    @DisplayName("회원가입 성공")
    void createGuest() throws Exception {
        //given

        CreateGuestTestRequest request = new CreateGuestTestRequest(
                171, 1, 61, 25, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        given(onBoardingMemberUsecase.execute(any(OnBoardingMemberCommand.class))).willReturn(OnBoardingMember.builder()
                .loginId("testId1234")
                .password("testPassword1234")
                .build());

        //when

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/members-guest")
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
                RestDocumentationRequestBuilders.post("/v1/members-guest")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));


        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.error.reason").value(invalidValue + "는 올바르지 않은 값입니다."));
        resultActions.andDo(document("create-guest-fail"));
    }


}