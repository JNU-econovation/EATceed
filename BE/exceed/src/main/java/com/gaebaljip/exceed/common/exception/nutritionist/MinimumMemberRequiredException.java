package com.gaebaljip.exceed.common.exception.nutritionist;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class MinimumMemberRequiredException extends EatCeedException {

    public static EatCeedException EXCEPTION = new MinimumMemberRequiredException();

    public MinimumMemberRequiredException() {
        super(NutritionistError.NOT_REQUIRED_MINIMUM_MEMBER);
    }
}
