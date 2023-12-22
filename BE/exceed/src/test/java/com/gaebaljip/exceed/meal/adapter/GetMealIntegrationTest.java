package com.gaebaljip.exceed.meal.adapter;

import com.gaebaljip.exceed.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class GetMealIntegrationTest extends IntegrationTest {

    @Test
    void getMeal() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(get("/v1/meal/2023-12-22")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());
    }
}
