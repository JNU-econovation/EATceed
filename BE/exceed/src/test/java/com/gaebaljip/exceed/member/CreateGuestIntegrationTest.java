package com.gaebaljip.exceed.member;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.member.adapter.in.CreateGuestTestRequest;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                post("/v1/members")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));


        long cnt = memberRepository.findAll().stream().count();

        //then
        int afterCnt = memberRepository.findAll().size();
        resultActions.andExpect(status().isCreated());
        Assertions.assertThat(afterCnt - beforeCnt).isEqualTo(1);
        Assertions.assertThat(afterCnt).isGreaterThan(1);
    }


}
