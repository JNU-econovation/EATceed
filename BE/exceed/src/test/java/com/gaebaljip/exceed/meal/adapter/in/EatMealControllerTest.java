package com.gaebaljip.exceed.meal.adapter.in;

import com.gaebaljip.exceed.common.CommonApiTest;
import com.gaebaljip.exceed.common.WithMockGuestUser;
import com.gaebaljip.exceed.dto.request.EatMealRequest;
import com.gaebaljip.exceed.meal.application.port.in.EatMealUsecase;
import com.gaebaljip.exceed.meal.application.port.in.UploadImageUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentRequest;
import static com.gaebaljip.exceed.common.util.ApiDocumentUtil.getDocumentResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EatMealController.class)
class EatMealControllerTest extends CommonApiTest {

    @MockBean
    private EatMealUsecase eatMealUsecase;
    @MockBean
    private UploadImageUsecase uploadImageUsecase;

    @Test
    @WithMockGuestUser
    void eatMeal() throws Exception {

        List<Long> foodIds = List.of(1L, 2L, 3L);
        //given
        EatMealRequest request = new EatMealRequest(1.5, foodIds, "LUNCH", "test.jpeg");

        given(uploadImageUsecase.execute(any())).willReturn("https://gaebaljip.s3.ap-northeast-2.amazonaws.com/test.jpeb_presignedUrl임");

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/v1/meal")
                        .content(om.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated());
        resultActions.andDo(document("eat-meal-success",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("multiple").type(JsonFieldType.NUMBER).description("몇 인분"),
                        fieldWithPath("foodIds").type(JsonFieldType.ARRAY).description("음식 아이디 리스트"),
                        fieldWithPath("mealType").type(JsonFieldType.STRING).description("식사 타입" +
                                "BREAKFAST, LUNCH, DINNER, SNACK"),
                        fieldWithPath("fileName").type(JsonFieldType.STRING).description("파일 이름 + 확장자 있어야함")
                )
        ));
    }
}