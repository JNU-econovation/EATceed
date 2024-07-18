package com.gaebaljip.exceed.integration.food;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.adapter.in.food.request.CreateFoodRequest;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;

class CreateFoodIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("음식 추가 : 성공")
    @WithMockUser
    void createFood() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
                        .name("민초마라탕")
                        .calorie(1000.0)
                        .carbohydrate(100.0)
                        .dietaryFiber(10.0)
                        .fat(10.0)
                        .protein(10.0)
                        .servingSize(1.0)
                        .sodium(10.0)
                        .sugars(10.0)
                        .build();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/food")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        // then
        resultActions.andExpect(status().isOk());
    }
}
