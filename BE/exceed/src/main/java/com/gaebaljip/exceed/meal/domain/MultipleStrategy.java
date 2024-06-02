package com.gaebaljip.exceed.meal.domain;

public class MultipleStrategy implements MeasureStrategy {
    @Override
    public double measure(double value, Unit unit) {
        return value * unit.getMultiple();
    }
}
