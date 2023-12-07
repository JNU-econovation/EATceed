package com.gaebaljip.exceed.dto;

import com.gaebaljip.exceed.member.domain.Activity;
import lombok.Builder;

public record CreateMemberRequest(
        Integer height,
        Boolean gender,
        Integer weight,
        Integer age,
        Activity activity,
        String etc
) {

        @Builder
        public CreateMemberRequest {
        }
}
