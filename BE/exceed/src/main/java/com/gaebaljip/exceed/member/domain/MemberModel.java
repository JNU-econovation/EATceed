package com.gaebaljip.exceed.member.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MemberModel {

    private Double height;
    private Boolean gender;
    private Double weight;
    private Integer age;
    private Activity activity;
    private String etc;
}
