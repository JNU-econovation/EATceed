package com.gaebaljip.exceed.application.meal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gaebaljip.exceed.adapter.out.jpa.food.FoodEntity;
import com.gaebaljip.exceed.application.domain.meal.MealEntity;
import com.gaebaljip.exceed.application.domain.meal.MealType;
import com.gaebaljip.exceed.application.domain.member.MemberEntity;
import com.gaebaljip.exceed.application.port.in.meal.EatMealCommand;
import com.gaebaljip.exceed.application.port.out.food.FoodPort;
import com.gaebaljip.exceed.application.port.out.meal.MealFoodPort;
import com.gaebaljip.exceed.application.port.out.meal.MealPort;
import com.gaebaljip.exceed.application.port.out.member.MemberPort;
import com.gaebaljip.exceed.application.service.meal.EatMealService;
import com.gaebaljip.exceed.common.exception.meal.InvalidGException;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleAndGException;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleException;
import com.gaebaljip.exceed.dto.EatMealFoodDTO;

@ExtendWith(MockitoExtension.class)
class EatMealServiceTest {

    @Mock private FoodPort foodPort;
    @Mock private MemberPort memberPort;
    @Mock private MealPort mealPort;
    @Mock private MealFoodPort mealFoodPort;
    @InjectMocks private EatMealService eatMealService;

    @Test
    void when_NullMultipleAndNullGs_expected_Exception() {
        // when,then
        EatMealCommand eatMealCommand = getEatMealCommand(null, null, null, null);

        assertThrows(
                InvalidMultipleAndGException.class, () -> eatMealService.execute(eatMealCommand));
    }

    @Test
    void when_ValidMultipleAndNullG_NoException() {
        List<FoodEntity> foodEntities = new ArrayList<>(); // FoodEntity 리스트 생성
        foodEntities.add(FoodEntity.builder().id(1L).build()); // FoodEntity 추가
        EatMealCommand command = getEatMealCommand(1.0, null, null, 200);

        given(foodPort.queryAllEntities(anyList())).willReturn(foodEntities); // foodEntities 반환
        given(memberPort.query(anyLong())).willReturn(mock(MemberEntity.class));
        given(mealPort.command(any())).willReturn(mock(MealEntity.class));

        doAnswer(invocation -> null).when(mealFoodPort).command(any());

        eatMealService.execute(command); // Should not throw any exception
    }

    @Test
    void when_NullMultipleAndValidG_NoException() {
        List<FoodEntity> foodEntities = new ArrayList<>(); // FoodEntity 리스트 생성
        foodEntities.add(FoodEntity.builder().id(1L).build()); // FoodEntity 추가
        EatMealCommand command = getEatMealCommand(null, 3.0, 50, null);
        given(foodPort.queryAllEntities(anyList())).willReturn(foodEntities);
        given(memberPort.query(anyLong())).willReturn(mock(MemberEntity.class));
        given(mealPort.command(any())).willReturn(mock(MealEntity.class));
        doAnswer(invocation -> null).when(mealFoodPort).command(any());
        eatMealService.execute(command); // Should not throw any exception
    }

    @Test
    void when_InvalidMultiple_Expected_Exception() {
        EatMealCommand eatMealCommand = getEatMealCommand(-1.0, null, null, 100);
        assertThrows(InvalidMultipleException.class, () -> eatMealService.execute(eatMealCommand));
    }

    @Test
    void when_InvalidG_Expected_Exception() {
        EatMealCommand eatMealCommand = getEatMealCommand(null, 3.0, 0, null);
        assertThrows(InvalidGException.class, () -> eatMealService.execute(eatMealCommand));
    }

    public EatMealCommand getEatMealCommand(
            Double multiple1, Double multiple2, Integer g1, Integer g2) {
        Long foodId1 = 1L;
        Long foodId2 = 2L;
        MealType mealType = MealType.BREAKFAST;
        List<EatMealFoodDTO> eatMealFoodDTOS = new ArrayList<>();
        eatMealFoodDTOS.add(new EatMealFoodDTO(foodId1, multiple1, g1));
        eatMealFoodDTOS.add(new EatMealFoodDTO(foodId2, multiple2, g2));
        return EatMealCommand.builder()
                .eatMealFoodDTOS(eatMealFoodDTOS)
                .memberId(1L)
                .mealType(mealType)
                .build();
    }
}
