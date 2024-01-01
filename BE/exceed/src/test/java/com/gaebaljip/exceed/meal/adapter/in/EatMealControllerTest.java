package com.gaebaljip.exceed.meal.adapter.in;

import com.gaebaljip.exceed.common.CommonApiTest;
import com.gaebaljip.exceed.dto.request.EatMealRequest;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.in.UploadImageUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EatMealController.class)
class EatMealControllerTest extends CommonApiTest {

    @MockBean
    private EatMealUsecase eatMealUsecase;
    @MockBean
    private UploadImageUsecase uploadImageUsecase;

    @Test
    void eatMeal() throws Exception {

        List<Long> foodIds = List.of(1L, 2L, 3L);
        //given
        EatMealRequest request = new EatMealRequest(1.5, foodIds, "LUNCH", "test.jpeg");

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/v1/meal")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated());
    }
}