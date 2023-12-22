package com.gaebaljip.exceed.meal.adapter;

import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.dto.EatMealRequest;
import com.gaebaljip.exceed.meal.adapter.out.MealRepository;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.in.GetPreSignedUrlUsecase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EatMealIntegrationTest extends IntegrationTest {

    @Autowired
    private EatMealUsecase eatMealUsecase;

    @Autowired
    private GetPreSignedUrlUsecase getPreSignedUrlUsecase;

    @Autowired
    private MealRepository mealRepository;

    @Test
    void eatMeal() throws Exception {
        //given
        EatMealRequest eatMealRequest = EatMealRequest.builder()
                .mealType("LUNCH")
                .multiple(1.5)
                .foodIds(List.of(1L, 2L))
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/v1/meal")
                        .content(om.writeValueAsString(eatMealRequest))
                        .contentType(MediaType.APPLICATION_JSON));


        long cnt = mealRepository.findAll().stream().count();

        //then
        resultActions.andExpect(status().isCreated());
        Assertions.assertThat(cnt).isEqualTo(1);
    }


}
