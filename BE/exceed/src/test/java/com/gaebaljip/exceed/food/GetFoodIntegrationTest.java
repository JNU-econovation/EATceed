// package com.gaebaljip.exceed.food;
//
// import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
// import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
// import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.http.MediaType;
// import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
// import org.springframework.test.web.servlet.ResultActions;
//
// import com.gaebaljip.exceed.common.IntegrationTest;
//
// public class GetFoodIntegrationTest extends IntegrationTest {
//    String PRE_FIX = "prefix";
//
//    @Test
//    void getFoods() throws Exception {
//        // given
//        String prefix = "감";
//
//        ResultActions resultActions =
//                mockMvc.perform(
//                        RestDocumentationRequestBuilders.get("/v1/foods")
//                                .param(PRE_FIX, prefix)
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
//        ;
//    }
//
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
// }
