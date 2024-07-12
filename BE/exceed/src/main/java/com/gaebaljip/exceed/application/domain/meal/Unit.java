package com.gaebaljip.exceed.application.domain.meal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Unit {
    private double servingSize;
    private Integer g;
    private Double multiple;
}
