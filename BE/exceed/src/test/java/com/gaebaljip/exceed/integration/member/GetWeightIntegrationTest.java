package com.gaebaljip.exceed.integration.member;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.adapter.out.jpa.member.MemberRepository;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;

public class GetWeightIntegrationTest extends IntegrationTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @WithMockUser(memberId = 1L)
    @DisplayName("몸무게 조회 성공")
    void when_getWeight_expected_success() throws Exception {
        // given
        long memberId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/v1/members/weight"));

        MemberEntity member = memberRepository.findById(memberId).get();

        // then
        resultActions.andExpect(status().isOk());
        assertAll(
                () -> assertEquals(member.getWeight(), 70.0),
                () -> assertEquals(member.getTargetWeight(), 68.0));
    }
}
