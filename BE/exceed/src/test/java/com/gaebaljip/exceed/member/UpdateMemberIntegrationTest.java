package com.gaebaljip.exceed.member;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.dto.request.UpdateMemberRequest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberEntity;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;
import com.gaebaljip.exceed.member.exception.MemberError;

public class UpdateMemberIntegrationTest extends IntegrationTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @WithMockUser(memberId = 1L)
    @DisplayName("회원 수정 성공")
    void when_updateMember_expected_success() throws Exception {
        // given
        Long memberId = 1L;

        UpdateMemberRequest updateMemberRequest =
                UpdateMemberRequest.builder()
                        .height(180.3)
                        .activity("VERY_ACTIVE")
                        .age(40)
                        .gender("MALE")
                        .etc("회원 수정")
                        .build();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        put("/v1/members")
                                .content(om.writeValueAsString(updateMemberRequest))
                                .contentType(MediaType.APPLICATION_JSON));
        MemberEntity member = memberRepository.findById(memberId).get();
        // then
        resultActions.andExpectAll(status().isOk());
        assertAll(
                () -> assertEquals(member.getHeight(), updateMemberRequest.height()),
                () -> assertEquals(member.getActivity().name(), updateMemberRequest.activity()),
                () -> assertEquals(member.getAge(), updateMemberRequest.age()),
                () -> assertEquals(member.getGender().name(), updateMemberRequest.gender()),
                () -> assertEquals(member.getEtc(), updateMemberRequest.etc()));
    }

    @Test
    @WithMockUser(memberId = 1000L)
    @DisplayName("회원 수정 실패 - 회원이 존재하지 않음")
    void when_updateMember_member_invalid_expected_exception() throws Exception {
        // given
        UpdateMemberRequest updateMemberRequest =
                UpdateMemberRequest.builder()
                        .height(180.3)
                        .activity("VERY_ACTIVE")
                        .age(40)
                        .gender("MALE")
                        .etc("회원 수정")
                        .build();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        put("/v1/members")
                                .content(om.writeValueAsString(updateMemberRequest))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value(MemberError.INVALID_MEMBER.getReason()));
    }
}
