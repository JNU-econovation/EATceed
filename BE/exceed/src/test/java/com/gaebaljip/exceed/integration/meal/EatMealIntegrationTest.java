package com.gaebaljip.exceed.integration.meal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.adapter.in.meal.request.EatMealRequest;
import com.gaebaljip.exceed.adapter.out.jpa.meal.MealFoodRepository;
import com.gaebaljip.exceed.adapter.out.jpa.meal.MealRepository;
import com.gaebaljip.exceed.common.InitializeS3Bucket;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.dto.EatMealFoodDTO;

@InitializeS3Bucket
public class EatMealIntegrationTest extends IntegrationTest {

    @Autowired private MealRepository mealRepository;
    @Autowired private MealFoodRepository mealFoodRepository;

    @Test
    @DisplayName(
            "식사 등록 : 성공"
                    + "Meal 엔티티 저장 여부 확인"
                    + "MealFood 엔티티 저장 여부 확인 "
                    + "식사 등록 API 응답 코드 확인"
                    + "presignedUrl 반환 확인")
    @WithMockUser
    void when_eatMeal_expected_ReturnPresignedUrlAndSaveMeal() throws Exception {
        // given

        long beforeMealCnt = mealRepository.findAll().stream().count();
        long beforeMealFoodCnt = mealFoodRepository.findAll().stream().count();
        EatMealRequest request = getEatMealRequest();

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/v1/meal")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        long afterMealCnt = mealRepository.findAll().stream().count();
        long afterMealFoodCnt = mealFoodRepository.findAll().stream().count();
        resultActions.andExpectAll(
                status().isCreated(), jsonPath("$.response.presignedUrl").exists());
        assertAll(
                () -> {
                    Assertions.assertThat(afterMealCnt - beforeMealCnt).isEqualTo(1);
                    Assertions.assertThat(afterMealFoodCnt - beforeMealFoodCnt).isEqualTo(2);
                });
    }

    private EatMealRequest getEatMealRequest() {
        EatMealFoodDTO eatMealFoodDTO1 =
                EatMealFoodDTO.builder().foodId(1L).g(100).multiple(null).build();
        EatMealFoodDTO eatMealFoodDTO2 =
                EatMealFoodDTO.builder().foodId(3L).g(null).multiple(1.2).build();
        EatMealRequest request =
                new EatMealRequest(List.of(eatMealFoodDTO1, eatMealFoodDTO2), "LUNCH", "test.jpeg");
        return request;
    }
}
