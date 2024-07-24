package com.gaebaljip.exceed.adapter.in.meal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.adapter.in.meal.request.EatMealRequest;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.common.dto.EatMealFoodDTO;

class EatMealControllerTest extends ControllerTest {

    @Test
    @WithMockUser
    void when_eatMeal_gNotNullAndMultipleNull_expected_returnPresignedUrl() throws Exception {

        // given
        EatMealFoodDTO eatMealFoodDTO = new EatMealFoodDTO(1L, null, 100);
        EatMealRequest request = new EatMealRequest(List.of(eatMealFoodDTO), "LUNCH", "test.jpeg");

        given(uploadImageUsecase.execute(any()))
                .willReturn(
                        "https://gaebaljip.s3.ap-northeast-2.amazonaws.com/test.jpeb_presignedUrl");

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/v1/meal")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void when_eatMeal_withEmptyFileName_thenBadRequest() throws Exception {
        // given
        EatMealFoodDTO eatMealFoodDTO = new EatMealFoodDTO(1L, null, 100);
        EatMealRequest request = new EatMealRequest(List.of(eatMealFoodDTO), "LUNCH", "");

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/v1/meal")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.reason").value("파일명을 " + ValidationMessage.NOT_BLANK));
    }

    @Test
    @WithMockUser
    void when_eatMeal_withInvalidMealType_thenBadRequest() throws Exception {
        // given
        EatMealFoodDTO eatMealFoodDTO = new EatMealFoodDTO(1L, null, 100);
        EatMealRequest request =
                new EatMealRequest(List.of(eatMealFoodDTO), "INVALID_MEAL_TYPE", "test.jpeg");

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/v1/meal")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.error.reason")
                                .value("INVALID_MEAL_TYPE" + ValidationMessage.ENUM_SUFFIX));
    }

    @Test
    @WithMockUser
    void when_eatMeal_withNullFoodId_thenBadRequest() throws Exception {
        // given
        EatMealFoodDTO eatMealFoodDTO = new EatMealFoodDTO(null, null, 100);
        EatMealRequest request = new EatMealRequest(List.of(eatMealFoodDTO), "LUNCH", "test.jpeg");

        // when
        ResultActions resultActions =
                mockMvc.perform(
                        post("/v1/meal")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.reason").value("음식PK를 " + ValidationMessage.NOT_NULL));
    }
}
