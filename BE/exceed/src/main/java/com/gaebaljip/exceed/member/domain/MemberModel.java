package com.gaebaljip.exceed.member.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MemberModel {

    private PhysiqueModel physiqueModel;
    private String etc;

}
