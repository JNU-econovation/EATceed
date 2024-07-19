package com.gaebaljip.exceed.integration.member;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.adapter.in.member.request.UpdateWeightRequest;
import com.gaebaljip.exceed.adapter.out.jpa.member.MemberRepository;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;

public class UpdateWeightIntegrationTest extends IntegrationTest {
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("몸무게 수정 성공")
    @WithMockUser(memberId = 1L)
    void when_updateWeight_expected_success() throws Exception {
        // given
        Long memberId = 1L;
        UpdateWeightRequest updateWeightRequest =
                UpdateWeightRequest.builder().weight(50.0).targetWeight(70.5).build();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        patch("/v1/members/weight")
                                .content(om.writeValueAsString(updateWeightRequest))
                                .contentType(MediaType.APPLICATION_JSON));
        MemberEntity member = memberRepository.findById(memberId).get();

        // then
        resultActions.andExpectAll(status().isOk());
        assertAll(
                () -> assertEquals(updateWeightRequest.weight(), member.getWeight()),
                () -> assertEquals(updateWeightRequest.targetWeight(), member.getTargetWeight()));
    }
}
