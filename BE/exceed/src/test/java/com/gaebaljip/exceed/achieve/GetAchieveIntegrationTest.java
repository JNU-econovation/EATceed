package com.gaebaljip.exceed.achieve;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockGuestUser;
import com.gaebaljip.exceed.dto.response.GetAchieveListResponse;
import com.gaebaljip.exceed.meal.adapter.out.MealPersistenceAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MealPersistenceAdapter mealPersistenceAdapter;

    @Test
    @Transactional
    @WithMockGuestUser
    void getAchieves() throws Exception {
        //given
        String year = "2023";
        String month = "12";
        String day = "24";
        String date = year + "-" + month + "-" + day;
        LocalDate testDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        int comparisonSize = mealPersistenceAdapter.queryForMonthAchievements(1L, testDate).size();

        //when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/v1/achieve/" + date)
                        .contentType(MediaType.APPLICATION_JSON));
        //then

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);
        ApiResponse.CustomBody<GetAchieveListResponse> getAchieveListResponseCustomBody = om.readValue(responseBody, new TypeReference<ApiResponse.CustomBody<GetAchieveListResponse>>() {
        });

        int comparedSize = getAchieveListResponseCustomBody.getResponse().getAchieves().stream().filter(achieve -> achieve.isVisited().equals(true)).toList().size();

        System.out.println("comparedSize = " + comparedSize);
        System.out.println("comparisonSize = " + comparisonSize);
        Assertions.assertThat(comparedSize).isEqualTo(comparisonSize);
        resultActions.andExpect(status().isOk())
                .andDo(document("get-achieves-success",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

}
