package com.gaebaljip.exceed.application.domain.meal;

import static com.gaebaljip.exceed.application.domain.meal.MealFoodEntity.ENTITY_PREFIX;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Unit {
    @Column(name = ENTITY_PREFIX + "_G")
    private Integer g;

    @Column(name = ENTITY_PREFIX + "_MULTIPLE")
    private Double multiple;

    protected MeasureStrategy getStrategy() {
        if (this.getG() == null) {
            return new MultipleStrategy();
        } else {
            return new GStrategy();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(g, unit.g) && Objects.equals(multiple, unit.multiple);
    }

    @Override
    public int hashCode() {
        return Objects.hash(g, multiple);
    }
}
