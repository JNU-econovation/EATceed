package com.gaebaljip.exceed.achieve.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DailyRecord {

    private Double currentCalorie;
    private Double currentCarbohydrate;
    private Double currentProtein;
    private Double currentFat;
    private LocalDate date;
}