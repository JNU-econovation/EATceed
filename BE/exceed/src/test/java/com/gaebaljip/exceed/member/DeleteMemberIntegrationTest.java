package com.gaebaljip.exceed.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;

public class DeleteMemberIntegrationTest extends IntegrationTest {
    @Autowired private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MemberEntity memberEntity =
                MemberEntity.builder()
                        .email("aaa@naver.com")
                        .password("aaaa1234@@")
                        .checked(false)
                        .build();
        memberRepository.save(memberEntity);
    }

    @Test
    @WithMockUser(memberId = 1L)
    @DisplayName("회원 탈퇴 성공")
    void deleteMember() throws Exception {

        // given

        // when
        ResultActions resultActions = mockMvc.perform(delete("/v1/members"));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @WithMockUser(memberId = 100L)
    @DisplayName("회원 탈퇴 실패 : 회원이 존재하지 않음")
    void deleteMember_fail() throws Exception {

        // given

        // when
        ResultActions resultActions = mockMvc.perform(delete("/v1/members"));

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        // then
        resultActions.andExpectAll(status().isBadRequest(), jsonPath("$.error.code").value("4448"));
    }
}
