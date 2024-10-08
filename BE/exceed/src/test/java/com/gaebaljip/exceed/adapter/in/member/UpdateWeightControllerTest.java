package com.gaebaljip.exceed.adapter.in.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.adapter.in.member.request.UpdateWeightRequest;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.event.Events;

@RecordApplicationEvents
public class UpdateWeightControllerTest extends ControllerTest {

    @Autowired ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        Events.setPublisher(applicationEventPublisher);
    }

    @AfterEach
    void tearDown() {
        Events.reset();
    }

    @Test
    @WithMockUser(memberId = 1L)
    @DisplayName("몸무게 수정 성공")
    void when_updateWeight_expected_success() throws Exception {
        // given
        UpdateWeightRequest updateWeightRequest =
                UpdateWeightRequest.builder().weight(50.0).targetWeight(70.5).build();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        patch("/v1/members/weight")
                                .content(om.writeValueAsString(updateWeightRequest))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(status().isOk());
    }
}
