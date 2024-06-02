package com.gaebaljip.exceed.meal.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Unit {
    private double servingSize;
    private Integer g;
    private Double multiple;
}
