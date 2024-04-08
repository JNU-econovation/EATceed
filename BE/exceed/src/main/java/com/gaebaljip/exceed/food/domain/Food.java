package com.gaebaljip.exceed.food.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Food {

    private Long id;
    private String name;
    private double calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
}
