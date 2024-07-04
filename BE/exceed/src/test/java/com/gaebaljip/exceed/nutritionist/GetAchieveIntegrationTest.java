package com.gaebaljip.exceed.nutritionist;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;

public class GetAchieveIntegrationTest extends IntegrationTest {
    @Test
    @Transactional
    @WithMockUser
    void getAchieves() throws Exception {
        // given
        LocalDate testData = LocalDate.now();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/achieve/" + testData)
                                .contentType(MediaType.APPLICATION_JSON));
        // then

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        ApiResponse.CustomBody<GetAnalysisResponse> getAchieveListResponseCustomBody =
                om.readValue(
                        responseBody,
                        new TypeReference<ApiResponse.CustomBody<GetAnalysisResponse>>() {});

        int comparedSize =
                getAchieveListResponseCustomBody.getResponse().getAnalyses().stream()
                        .toList()
                        .size();

        Assertions.assertThat(comparedSize).isEqualTo(testData.lengthOfMonth());
        resultActions
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "get-achieves-success",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
}
