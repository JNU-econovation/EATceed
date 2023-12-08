package com.gaebaljip.exceed.dto;

import com.gaebaljip.exceed.member.domain.Activity;
import lombok.Builder;

public record CreateMemberRequest(
        Double height,
        Boolean gender,
        Double weight,
        Integer age,
        Activity activity,
        String etc
) {

        @Builder
        public CreateMemberRequest {
        }
}
