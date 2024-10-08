package com.gaebaljip.exceed.common;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaebaljip.exceed.adapter.in.auth.AuthController;
import com.gaebaljip.exceed.adapter.in.food.CreateFoodController;
import com.gaebaljip.exceed.adapter.in.meal.EatMealController;
import com.gaebaljip.exceed.adapter.in.meal.GetMealController;
import com.gaebaljip.exceed.adapter.in.member.OnBoardingController;
import com.gaebaljip.exceed.adapter.in.member.UpdateMemberController;
import com.gaebaljip.exceed.adapter.in.member.UpdateWeightController;
import com.gaebaljip.exceed.adapter.in.notify.EmitterController;
import com.gaebaljip.exceed.adapter.in.nutritionist.GetAnalysisController;
import com.gaebaljip.exceed.application.port.in.meal.*;
import com.gaebaljip.exceed.application.port.in.member.*;
import com.gaebaljip.exceed.application.port.in.notify.ConnectEmitterUseCase;
import com.gaebaljip.exceed.application.port.in.nutritionist.GetMonthlyAnalysisUsecase;
import com.gaebaljip.exceed.application.port.in.nutritionist.ValidateSignUpBeforeMonthUsecase;
import com.gaebaljip.exceed.application.service.auth.AuthService;
import com.gaebaljip.exceed.application.service.food.CreateFoodService;
import com.gaebaljip.exceed.application.service.member.OnBoardingMemberService;
import com.gaebaljip.exceed.application.service.nutritionist.GetAnalysisService;
import com.gaebaljip.exceed.application.service.nutritionist.GetDailyAnalysisService;

@ActiveProfiles("test")
@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest({
    AuthController.class,
    CreateFoodController.class,
    EatMealController.class,
    GetMealController.class,
    OnBoardingController.class,
    UpdateMemberController.class,
    UpdateWeightController.class,
    GetAnalysisController.class,
    EmitterController.class
})
@MockBeans({
    @MockBean(AuthService.class),
    @MockBean(CreateFoodService.class),
    @MockBean(EatMealUsecase.class),
    @MockBean(UploadImageUsecase.class),
    @MockBean(GetMaintainNutritionUsecase.class),
    @MockBean(GetTargetNutritionUsecase.class),
    @MockBean(GetWeightUseCase.class),
    @MockBean(GetCurrentMealQuery.class),
    @MockBean(GetSpecificMealQuery.class),
    @MockBean(GetDailyAnalysisService.class),
    @MockBean(ValidateBeforeSignUpDateUsecase.class),
    @MockBean(OnBoardingMemberService.class),
    @MockBean(UpdateMemberUsecase.class),
    @MockBean(UpdateWeightUsecase.class),
    @MockBean(GetMonthlyAnalysisUsecase.class),
    @MockBean(ConnectEmitterUseCase.class),
    @MockBean(ValidateSignUpBeforeMonthUsecase.class),
    @MockBean(GetAnalysisService.class)
})
public abstract class ControllerTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper om;
    @Autowired private WebApplicationContext webApplicationContext;
    @Autowired protected AuthService authService;
    @Autowired protected UploadImageUsecase uploadImageUsecase;
    @Autowired protected GetMaintainNutritionUsecase getMaintainNutritionUsecase;
    @Autowired protected GetTargetNutritionUsecase getTargetNutritionUsecase;
    @Autowired protected GetCurrentMealQuery getCurrentMealQuery;
    @Autowired protected ConnectEmitterUseCase connectEmitterUseCase;
    @Autowired protected GetWeightUseCase getWeightUseCase;
    @Autowired protected GetAnalysisService getAnalysisService;

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                        .alwaysDo(print())
                        .apply(documentationConfiguration(restDocumentation))
                        .build();
    }
}
