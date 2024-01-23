package com.gaebaljip.exceed.food;

import com.gaebaljip.exceed.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetFoodIntegrationTest extends IntegrationTest {

    @Test
    void getFoods() throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/foods")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andDo(document("get-food-noQueryString-success",
                        getDocumentRequest(),
                        getDocumentResponse()));;
    }

    @Test
    void getFoods1() throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/foods?lastFoodName=닭고기대파꼬치구이")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andDo(document("get-foods-lastFoodName-success",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }


    @Test
    void getFoods2() throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/foods?size=15")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andDo(document("get-foods-lastFoodNameAndSize-success",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    void getFoods3() throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/foods?keyword=구이")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andDo(document("get-foods-searchForKeyword-success",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    void getFoods4() throws Exception {
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/foods?keyword=구이&lastFoodName=닭발구이")
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions.andExpect(status().isOk())
                .andDo(document("get-foods-searchForKeywordAndLastFoodName-success",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

}
