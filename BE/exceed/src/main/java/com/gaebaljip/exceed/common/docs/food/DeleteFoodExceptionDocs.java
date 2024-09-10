package com.gaebaljip.exceed.common.docs.food;

import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.exception.food.CannotDeleteOthersFoodException;
import com.gaebaljip.exceed.common.exception.food.FoodNotFoundException;
import com.gaebaljip.exceed.common.swagger.ExceptionDoc;
import com.gaebaljip.exceed.common.swagger.ExplainError;
import com.gaebaljip.exceed.common.swagger.SwaggerExampleExceptions;

@ExceptionDoc
public class DeleteFoodExceptionDocs implements SwaggerExampleExceptions {
    @ExplainError("다른 사람의 음식을 삭제하려고 할 때")
    public EatCeedException 다른_사람의_음식을_삭제하려고_할_때 = CannotDeleteOthersFoodException.Exception;

    @ExplainError("음식을 찾을 수 없을 때")
    public EatCeedException 음식을_찾을_수_없을_때 = FoodNotFoundException.Exception;
}
