package com.gaebaljip.exceed.common.swagger;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ExplainError {
    String value() default "";
}
