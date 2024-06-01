package com.gaebaljip.exceed.config;

import io.swagger.v3.oas.models.examples.Example;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SwaggerExampleHolder {
    private Example holder;
    private String name;
    private int code;
}
