package com.gaebaljip.exceed.integration.food;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gaebaljip.exceed.adapter.out.redis.RedisUtils;
import com.gaebaljip.exceed.common.EatCeedStaticMessage;
import com.gaebaljip.exceed.common.IntegrationTest;
import com.gaebaljip.exceed.common.data.RedisAutoComplete;
import com.gaebaljip.exceed.common.exception.food.FoodError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetFoodIntegrationTest extends IntegrationTest {
    String PRE_FIX = "prefix";

    @Autowired private RedisUtils redisUtils;
    @Autowired private ApplicationArguments applicationArguments;

    @BeforeEach
    void setUp() throws Exception {
        RedisAutoComplete redisAutoComplete = new RedisAutoComplete(redisUtils);
        redisAutoComplete.run(applicationArguments);
    }

    @AfterEach
    void tearDown() {
        redisUtils.deleteData(EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY);
    }

    @Test
    @DisplayName("음식 자동완성 : 성공")
    void when_getFoodsAuto_expected_success() throws Exception {
        // given
        String prefix = "감";
        Long size = redisUtils.zSize(EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY);

        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/foods/auto")
                                .queryParam(PRE_FIX, prefix)
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.response.foodJson[0]").value("감자된장국:21;"),
                        jsonPath("$.response.foodJson[1]").value("감자볶음:22;"))
                .andDo(
                        document(
                                "get-food-noQueryString-success",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("음식 정보 조회 성공")
    void when_getFood_expected_success() throws Exception {
        // given
        Long foodId = 1L;

        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/foods/{foodId}", foodId)
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.response.foodId").value(1L),
                        jsonPath("$.response.sugars").value(3.1),
                        jsonPath("$.response.dietaryFiber").value(2.3),
                        jsonPath("$.response.sodium").value(500),
                        jsonPath("$.response.name").value("사과"),
                        jsonPath("$.response.calorie").value(200.0),
                        jsonPath("$.response.carbohydrate").value(30.0),
                        jsonPath("$.response.protein").value(2.0),
                        jsonPath("$.response.fat").value(10.0),
                        jsonPath("$.response.servingSize").value(120.0))
                .andDo(
                        document(
                                "get-food-noQueryString-success",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }

    @Test
    @DisplayName("음식 정보 조회 실패 - 존재하지 않는 음식")
    void when_getFood_expected_fail() throws Exception {
        // given
        Long foodId = 100L;

        ResultActions resultActions =
                mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/foods/{foodId}", foodId)
                                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();

        resultActions
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.error.code").value(FoodError.INVALID_FOOD.getCode()),
                        jsonPath("$.error.reason").value(FoodError.INVALID_FOOD.getReason()))
                .andDo(
                        document(
                                "get-food-noQueryString-fail",
                                getDocumentRequest(),
                                getDocumentResponse()));
    }
}
