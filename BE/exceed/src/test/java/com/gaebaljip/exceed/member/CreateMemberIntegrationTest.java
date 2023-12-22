package com.gaebaljip.exceed.member;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.member.adapter.in.CreateMemberController;
import com.gaebaljip.exceed.member.adapter.in.CreateMemberTestRequest;
import com.gaebaljip.exceed.member.application.MemberConverter;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberPersistenceAdapter;
import com.gaebaljip.exceed.member.adapter.out.persistence.MemberRepository;
import com.gaebaljip.exceed.member.application.CreateMemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateMemberIntegrationTest extends IntegrationTest {

    @Autowired
    private CreateMemberController createMemberController;

    @Autowired
    private CreateMemberService createMemberService;

    @Autowired
    private MemberConverter memberConverter;

    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void createMember() throws Exception {
        //given
        int beforeCnt = memberRepository.findAll().size();
        CreateMemberTestRequest request = new CreateMemberTestRequest(
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
