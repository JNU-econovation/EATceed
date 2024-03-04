package com.gaebaljip.exceed.nutritionist;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockGuestUser;
import com.gaebaljip.exceed.dto.response.GetAnalysisResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAchieveIntegrationTest extends IntegrationTest {
    @Test
    @Transactional
    @WithMockGuestUser
    void getAchieves() throws Exception {
        //given
        String year = "2024";
        String month = "03";
        String day = "03";
        String date = year + "-" + month + "-" + day;
        LocalDate testDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));

        //when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/achieve/" + date)
                        .contentType(MediaType.APPLICATION_JSON));
        //then

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);
        ApiResponse.CustomBody<GetAnalysisResponse> getAchieveListResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<GetAnalysisResponse>>() {
        });

        int comparedSize = getAchieveListResponseCustomBody.getResponse().getAnalyses().stream().toList().size();

        System.out.println("comparedSize = " + comparedSize);
        Assertions.assertThat(comparedSize).isEqualTo(testDate.lengthOfMonth());
        resultActions.andExpect(status().isOk())
                .andDo(document("get-achieves-success",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

}
