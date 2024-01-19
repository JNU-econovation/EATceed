package com.gaebaljip.exceed.member;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.dto.response.CreateGuest;
import com.gaebaljip.exceed.member.adapter.in.CreateGuestTestRequest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateGuestIntegrationTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void createGuest() throws Exception {
        //given
        int beforeCnt = memberRepository.findAll().size();
        CreateGuestTestRequest request = new CreateGuestTestRequest(
                171, 1, 61, 25, "NOT_ACTIVE", "뭐든 잘 먹습니다.");

        //when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/v1/members-guest")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));


        long cnt = memberRepository.findAll().stream().count();

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        ApiResponse.CustomBody<CreateGuest> getMealFoodResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<CreateGuest>>() {
        });


        //then
        int afterCnt = memberRepository.findAll().size();
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(header().exists("Authorization"));
        Assertions.assertThat(getMealFoodResponseCustomBody.getResponse().loginId()).isNotNull();
        Assertions.assertThat(getMealFoodResponseCustomBody.getResponse().password()).isNotNull();
        Assertions.assertThat(afterCnt - beforeCnt).isEqualTo(1);
        Assertions.assertThat(afterCnt).isGreaterThan(1);
        resultActions.andExpect(status().isCreated())
                .andDo(document("create-guest-success",
                        responseHeaders(
                                headerWithName("Authorization").description("Access Token")
                        )));

    }


}
