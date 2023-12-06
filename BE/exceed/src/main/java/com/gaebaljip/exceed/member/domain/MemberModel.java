package com.gaebaljip.exceed.member.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MemberModel {

    private Integer height;
    private Boolean gender;
    private Integer weight;
    private Integer age;
    private Activity activity;
    private String etc;
}
