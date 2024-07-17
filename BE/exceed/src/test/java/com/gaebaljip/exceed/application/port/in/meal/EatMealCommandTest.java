package com.gaebaljip.exceed.application.port.in.meal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.gaebaljip.exceed.adapter.in.meal.request.EatMealRequest;
import com.gaebaljip.exceed.common.dto.EatMealFoodDTO;
import com.gaebaljip.exceed.common.exception.meal.InvalidGException;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleAndGException;
import com.gaebaljip.exceed.common.exception.meal.InvalidMultipleException;

class EatMealCommandTest {

    private String fileName = "testImage.jpg";

    @Test
    void when_NullMultipleAndValidG_NoException() {
        EatMealFoodDTO validDTO = EatMealFoodDTO.builder().foodId(1L).g(50).build();
        EatMealRequest request = new EatMealRequest(List.of(validDTO), "LUNCH", fileName);
        assertDoesNotThrow(() -> EatMealCommand.of(request, 1L));
    }

    @Test
    void when_InvalidMultiple_Expected_Exception() {
        EatMealFoodDTO invalidDTO =
                EatMealFoodDTO.builder().foodId(1L).multiple(150.0).build(); // multiple > 100
        EatMealRequest request = new EatMealRequest(List.of(invalidDTO), "DINNER", fileName);

        assertThrows(InvalidMultipleException.class, () -> EatMealCommand.of(request, 1L));
    }

    @Test
    void when_InvalidG_Expected_Exception() {
        EatMealFoodDTO invalidDTO = EatMealFoodDTO.builder().foodId(1L).g(-10).build(); // g <= 0
        EatMealRequest request = new EatMealRequest(List.of(invalidDTO), "BREAKFAST", fileName);

        assertThrows(InvalidGException.class, () -> EatMealCommand.of(request, 1L));
    }

    @Test
    void when_BothMultipleAndGProvided_Expected_Exception() {
        EatMealFoodDTO invalidDTO =
                EatMealFoodDTO.builder()
                        .foodId(1L)
                        .multiple(1.0)
                        .g(10)
                        .build(); // both multiple and g provided
        EatMealRequest request = new EatMealRequest(List.of(invalidDTO), "SNACK", fileName);

        assertThrows(InvalidMultipleAndGException.class, () -> EatMealCommand.of(request, 1L));
    }

    @Test
    void when_BothMultipleAndGNull_Expected_Exception() {
        EatMealFoodDTO invalidDTO =
                EatMealFoodDTO.builder().foodId(1L).build(); // both multiple and g null
        EatMealRequest request = new EatMealRequest(List.of(invalidDTO), "SNACK", fileName);

        assertThrows(InvalidMultipleAndGException.class, () -> EatMealCommand.of(request, 1L));
    }
}
