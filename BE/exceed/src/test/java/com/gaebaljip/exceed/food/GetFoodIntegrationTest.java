package com.gaebaljip.exceed.food;

import com.gaebaljip.exceed.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetFoodIntegrationTest extends IntegrationTest {

    @Test
    void getFoods() throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/foods")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andDo(document("get-food-success"));;
    }

    @Test
    void getFoods1() throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/foods?lastFoodName=수박&size=10")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andDo(document("get-foods-success"));;
    }
}
