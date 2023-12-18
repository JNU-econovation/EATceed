package com.gaebaljip.exceed.member.adapter.in;

public record CreateMemberTestRequest(
        Integer height,
        Integer gender,
        Integer weight,
        Integer age,
        String activity,
        String etc
) {

}
