package com.gaebaljip.exceed.adapter.in.food;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import com.gaebaljip.exceed.adapter.in.food.request.CreateFoodRequest;
import com.gaebaljip.exceed.common.ControllerTest;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.WithMockUser;

public class CreateFoodControllerTest extends ControllerTest {

    @Test
    @DisplayName("음식 추가 : 실패 - 이름이 없는 경우")
    @WithMockUser
    void createFoodFail() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
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

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("이름을 " + ValidationMessage.NOT_BLANK));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 칼로리가 없는 경우")
    @WithMockUser
    void createFoodFail2() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
                        .name("민초마라탕")
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

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("칼로리를 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 탄수화물이 없는 경우")
    @WithMockUser
    void createFoodFail3() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
                        .name("민초마라탕")
                        .calorie(1000.0)
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

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("탄수화물을 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 식이섬유가 없는 경우")
    @WithMockUser
    void createFoodFail4() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
                        .name("민초마라탕")
                        .calorie(1000.0)
                        .carbohydrate(100.0)
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

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("식이섬유를 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 지방이 없는 경우")
    @WithMockUser
    void createFoodFail5() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
                        .name("민초마라탕")
                        .calorie(1000.0)
                        .carbohydrate(100.0)
                        .dietaryFiber(10.0)
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

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("지방을 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 단백질이 없는 경우")
    @WithMockUser
    void createFoodFail6() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
                        .name("민초마라탕")
                        .calorie(1000.0)
                        .carbohydrate(100.0)
                        .dietaryFiber(10.0)
                        .fat(10.0)
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

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("단백질을 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 1회 제공량이 없는 경우")
    @WithMockUser
    void createFoodFail7() throws Exception {
        // given
        CreateFoodRequest request =
                CreateFoodRequest.builder()
                        .name("민초마라탕")
                        .calorie(1000.0)
                        .carbohydrate(100.0)
                        .dietaryFiber(10.0)
                        .fat(10.0)
                        .protein(10.0)
                        .sodium(10.0)
                        .sugars(10.0)
                        .build();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/food")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("1회 제공량을 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 당이 없는 경우")
    @WithMockUser
    void createFoodFail8() throws Exception {
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
                        .build();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/food")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("당을 " + ValidationMessage.NOT_NULL));
    }

    @Test
    @DisplayName("음식 추가 : 실패 - 나트륨이 없는 경우")
    @WithMockUser
    void createFoodFail9() throws Exception {
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
                        .sugars(10.0)
                        .build();
        // when
        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/food")
                                .content(om.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isBadRequest(),
                jsonPath("$.error.reason").value("나트륨을 " + ValidationMessage.NOT_NULL));
    }
}
