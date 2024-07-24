package com.gaebaljip.exceed.adapter.in.notify;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.WithMockUser;

public class EmitterControllerTest extends ControllerTest {

    @Test
    @WithMockUser(memberId = 1L)
    void when_connect_expected_success() throws Exception {

        // given
        given(connectEmitterUseCase.execute(any(), any())).willReturn(new SseEmitter());

        // when
        ResultActions resultActions =
                mockMvc.perform(get("/api/emitter/connect").contentType("text/event-stream"));

        // then
        resultActions.andExpect(status().isOk());
    }
}
