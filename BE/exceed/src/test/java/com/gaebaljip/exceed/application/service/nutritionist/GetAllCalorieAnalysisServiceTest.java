package com.gaebaljip.exceed.application.service.nutritionist;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaebaljip.exceed.adapter.in.nutritionist.request.GetAllAnalysisRequest;
import com.gaebaljip.exceed.application.domain.meal.DailyMealFoods;
import com.gaebaljip.exceed.application.domain.member.Member;
import com.gaebaljip.exceed.application.port.out.meal.DailyMealPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.common.dto.AllAnalysisDTO;
import com.gaebaljip.exceed.common.dto.DailyMealDTO;
import com.gaebaljip.exceed.common.factory.DailyMealFoodsFixtureFactory;
import com.gaebaljip.exceed.common.factory.MemberFixtureFactory;

@ExtendWith(MockitoExtension.class)
class GetAllCalorieAnalysisServiceTest {

    @Mock private DailyMealPort dailyMealPort;
    @Mock private MemberPort memberPort;

    @InjectMocks private GetAllCalorieAnalysisService getAllCalorieAnalysisService;

    @Test
    void when_mealFood_is_0_expected_isVisited_false_and_allAchieved_false() {

        // given
        LocalDateTime now = LocalDateTime.now();
        Member member = MemberFixtureFactory.create(1);
        GetAllAnalysisRequest request = getGetAllAnalysisRequest(now);
        DailyMealFoods dailyMealFoods =
                DailyMealFoodsFixtureFactory.create(now.toLocalDate(), now.toLocalDate(), 0);
        given(
                        dailyMealPort.queryDailyMealFoods(
                                new DailyMealDTO(request.memberId(), request.dateTime())))
                .willReturn(dailyMealFoods);
        given(memberPort.query(request.memberId(), request.dateTime())).willReturn(member);

        // when
        AllAnalysisDTO allAnalysisDTO = getAllCalorieAnalysisService.execute(request);

        // then
        assertAll(
                () -> assertFalse(allAnalysisDTO.isVisited()),
                () -> assertFalse(allAnalysisDTO.isCalorieAchieved()),
                () -> assertFalse(allAnalysisDTO.isCarbohydrateAchieved()),
                () -> assertFalse(allAnalysisDTO.isFatAchieved()),
                () -> assertFalse(allAnalysisDTO.isProteinAchieved()),
                () -> assertEquals(request.dateTime().toLocalDate(), now.toLocalDate()));
    }

    @Test
    void when_mealFood_is_3_expected_isVisited_false_and_allAchieved_false() {

        // given
        LocalDateTime now = LocalDateTime.now();
        Member member = MemberFixtureFactory.create(1);
        GetAllAnalysisRequest request = getGetAllAnalysisRequest(now);
        DailyMealFoods dailyMealFoods =
                DailyMealFoodsFixtureFactory.create(now.toLocalDate(), now.toLocalDate(), 3);
        given(
                        dailyMealPort.queryDailyMealFoods(
                                new DailyMealDTO(request.memberId(), request.dateTime())))
                .willReturn(dailyMealFoods);
        given(memberPort.query(request.memberId(), request.dateTime())).willReturn(member);

        // when
        AllAnalysisDTO allAnalysisDTO = getAllCalorieAnalysisService.execute(request);

        // then
        assertAll(
                () -> assertTrue(allAnalysisDTO.isVisited()),
                () -> assertEquals(request.dateTime().toLocalDate(), now.toLocalDate()));
    }

    private GetAllAnalysisRequest getGetAllAnalysisRequest(LocalDateTime localDateTime) {
        return new GetAllAnalysisRequest(1L, localDateTime);
    }
}
