// package com.gaebaljip.exceed.food;
//
// import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
// import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
// import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.ApplicationArguments;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
// import org.springframework.test.web.servlet.ResultActions;
//
// import com.gaebaljip.exceed.common.EatCeedStaticMessage;
// import com.gaebaljip.exceed.common.IntegrationTest;
// import com.gaebaljip.exceed.infrastructure.redis.RedisAutoComplete;
// import com.gaebaljip.exceed.infrastructure.redis.RedisUtils;
//
// import lombok.extern.log4j.Log4j2;
//
// @SpringBootTest(args = "../food_data.csv")
// @Log4j2
// public class GetFoodIntegrationTest extends IntegrationTest {
//    String PRE_FIX = "prefix";
//
//    @Autowired private RedisUtils redisUtils;
//    @Autowired private ApplicationArguments applicationArguments;
//
//    @BeforeEach
//    void setUp() {
//        RedisAutoComplete redisAutoComplete = new RedisAutoComplete(redisUtils);
//        try {
//            redisAutoComplete.run(applicationArguments);
//        } catch (Exception e) {
//            log.error("Failed to read CSV file: {}", e.getMessage());
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        redisUtils.deleteData(EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY);
//    }
//
//    @Test
//    void getFoods() throws Exception {
//        // given
//        String prefix = "감";
//
//        ResultActions resultActions =
//                mockMvc.perform(
//                        RestDocumentationRequestBuilders.get("/v1/foods/auto")
//                                .queryParam(PRE_FIX, prefix)
//                                .contentType(MediaType.APPLICATION_JSON));
//
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//
//        resultActions
//                .andExpect(status().isOk())
//                .andDo(
//                        document(
//                                "get-food-noQueryString-success",
//                                getDocumentRequest(),
//                                getDocumentResponse()));
//    }
// }
//    //    @Test
//    //    void getFoods1() throws Exception {
//    //
//    //        ResultActions resultActions =
//    //                mockMvc.perform(
//    //                        RestDocumentationRequestBuilders.get("/v1/foods")
//    //                                .param(PRE_FIX,)
//    //                                .contentType(MediaType.APPLICATION_JSON));
//    //
//    //        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//    //
//    //        resultActions
//    //                .andExpect(status().isOk())
//    //                .andDo(
//    //                        document(
//    //                                "get-foods-lastFoodName-success",
//    //                                getDocumentRequest(),
//    //                                getDocumentResponse()));
//    //    }
//    //
//    //    @Test
//    //    void getFoods2() throws Exception {
//    //        ResultActions resultActions =
//    //                mockMvc.perform(
//    //                        RestDocumentationRequestBuilders.get("/v1/foods?size=15")
//    //                                .contentType(MediaType.APPLICATION_JSON));
//    //
//    //        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//    //
//    //        resultActions
//    //                .andExpect(status().isOk())
//    //                .andDo(
//    //                        document(
//    //                                "get-foods-lastFoodNameAndSize-success",
//    //                                getDocumentRequest(),
//    //                                getDocumentResponse()));
//    //    }
//    //
//    //    @Test
//    //    void getFoods3() throws Exception {
//    //        ResultActions resultActions =
//    //                mockMvc.perform(
//    //                        RestDocumentationRequestBuilders.get("/v1/foods?keyword=구이")
//    //                                .contentType(MediaType.APPLICATION_JSON));
//    //
//    //        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//    //
//    //        resultActions
//    //                .andExpect(status().isOk())
//    //                .andDo(
//    //                        document(
//    //                                "get-foods-searchForKeyword-success",
//    //                                getDocumentRequest(),
//    //                                getDocumentResponse()));
//    //    }
//    //
//    //    @Test
//    //    void getFoods4() throws Exception {
//    //        ResultActions resultActions =
//    //                mockMvc.perform(
//    //                        RestDocumentationRequestBuilders.get(
//    //                                        "/v1/foods?keyword=구이&lastFoodName=닭발구이")
//    //                                .contentType(MediaType.APPLICATION_JSON));
//    //
//    //        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//    //
//    //        resultActions
//    //                .andExpect(status().isOk())
//    //                .andDo(
//    //                        document(
//    //                                "get-foods-searchForKeywordAndLastFoodName-success",
//    //                                getDocumentRequest(),
//    //                                getDocumentResponse()));
//    //    }
