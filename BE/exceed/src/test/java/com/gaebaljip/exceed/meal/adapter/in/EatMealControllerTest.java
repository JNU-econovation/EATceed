package com.gaebaljip.exceed.meal.adapter.in;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.WithMockUser;
import com.gaebaljip.exceed.dto.EatMealFoodDTO;
import com.gaebaljip.exceed.dto.request.EatMealRequest;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.in.UploadImageUsecase;

@WebMvcTest(EatMealController.class)
class EatMealControllerTest extends ControllerTest {

    @MockBean private EatMealUsecase eatMealUsecase;
    @MockBean private UploadImageUsecase uploadImageUsecase;

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
}
