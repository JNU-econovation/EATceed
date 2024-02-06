package com.gaebaljip.exceed.achieve.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DailyTarget {

    private Double targetCalorie;
    private Double targetCarbohydrate;
    private Double targetProtein;
    private Double targetFat;
    private LocalDate date;
}
