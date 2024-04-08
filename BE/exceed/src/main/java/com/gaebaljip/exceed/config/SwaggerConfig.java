package com.gaebaljip.exceed.config;

import static java.util.stream.Collectors.groupingBy;

import java.lang.reflect.Field;
import java.util.*;

import javax.servlet.ServletContext;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.common.exception.BaseError;
import com.gaebaljip.exceed.common.exception.EatCeedException;
import com.gaebaljip.exceed.common.swagger.ApiErrorCodeExample;
import com.gaebaljip.exceed.common.swagger.ApiErrorExceptionsExample;
import com.gaebaljip.exceed.common.swagger.DisableSwaggerSecurity;
import com.gaebaljip.exceed.common.swagger.ExplainError;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final ApplicationContext applicationContext;
    private static final String SWAGGER_DOCS_VERSION = "2.0";

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);
        return new OpenAPI().servers(List.of(server)).components(authSetting()).info(swaggerInfo());
    }

    private io.swagger.v3.oas.models.info.Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://github.com/JNU-econovation/EATceed");
        license.setName("EATceed");

        return new Info()
                .version(SWAGGER_DOCS_VERSION)
                .title("\"EATceed 서버 API문서\"")
                .description("EATceed 서버의 API 문서 입니다.")
                .license(license);
    }

    private Components authSetting() {
        return new Components()
                .addSecuritySchemes(
                        "access-token",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization"));
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }

    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            DisableSwaggerSecurity methodAnnotation =
                    handlerMethod.getMethodAnnotation(DisableSwaggerSecurity.class);
            ApiErrorExceptionsExample apiErrorExceptionsExample =
                    handlerMethod.getMethodAnnotation(ApiErrorExceptionsExample.class);
            ApiErrorCodeExample apiErrorCodeExample =
                    handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);

            List<String> tags = getTags(handlerMethod);
            // DisableSecurity 어노테이션있을시 스웨거 시큐리티 설정 삭제
            if (methodAnnotation != null) {
                operation.setSecurity(Collections.emptyList());
            }
            // 태그 중복 설정시 제일 구체적인 값만 태그로 설정
            if (!tags.isEmpty()) {
                operation.setTags(Collections.singletonList(tags.get(0)));
            }
            // ApiErrorExceptionsExample 어노테이션 단 메소드 적용
            if (apiErrorExceptionsExample != null) {
                generateExceptionResponseExample(operation, apiErrorExceptionsExample.value());
            }
            // ApiErrorCodeExample 어노테이션 단 메소드 적용
            if (apiErrorCodeExample != null) {
                generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
            }
            return operation;
        };
    }

    /**
     * BaseErrorCode 타입의 이넘값들을 문서화 시킵니다. ExplainError 어노테이션으로 부가설명을 붙일수있습니다. 필드들을 가져와서 예시 에러 객체를
     * 동적으로 생성해서 예시값으로 붙입니다.
     */
    private void generateErrorCodeResponseExample(
            Operation operation, Class<? extends BaseError> type) {
        ApiResponses responses = operation.getResponses();

        BaseError[] errorCodes = type.getEnumConstants();

        Map<Integer, List<SwaggerExampleHolder>> statusWithExampleHolders =
                Arrays.stream(errorCodes)
                        .map(
                                baseErrorCode -> {
                                    try {
                                        Error error = baseErrorCode.getError();
                                        return SwaggerExampleHolder.builder()
                                                .holder(
                                                        getSwaggerExample(
                                                                baseErrorCode.getExplainError(),
                                                                error))
                                                .code(Integer.parseInt(error.getStatus()))
                                                .name(error.getCode())
                                                .build();
                                    } catch (NoSuchFieldException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                        .collect(groupingBy(SwaggerExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    /**
     * SwaggerExampleExceptions 타입의 클래스를 문서화 시킵니다. SwaggerExampleExceptions 타입의 클래스는 필드로
     * ExceedException 타입을 가지며, ExceedException 의 error 와,ExplainError 의 설명을 문서화시킵니다.
     */
    private void generateExceptionResponseExample(Operation operation, Class<?> type) {
        ApiResponses responses = operation.getResponses();

        Object bean = applicationContext.getBean(type);
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        Map<Integer, List<SwaggerExampleHolder>> statusWithExampleHolders =
                Arrays.stream(declaredFields)
                        .filter(field -> field.getAnnotation(ExplainError.class) != null)
                        .filter(field -> field.getType() == EatCeedException.class)
                        .map(
                                field -> {
                                    try {
                                        EatCeedException exception =
                                                (EatCeedException) field.get(bean);
                                        ExplainError annotation =
                                                field.getAnnotation(ExplainError.class);
                                        String value = annotation.value();
                                        Error error = exception.getError();
                                        return SwaggerExampleHolder.builder()
                                                .holder(getSwaggerExample(value, error))
                                                .code(Integer.parseInt(error.getStatus()))
                                                .name(field.getName())
                                                .build();
                                    } catch (IllegalAccessException e) {
                                        // 문서가 생성되지 않으면 안되므로 런타임 예외를 던집니다.
                                        throw new RuntimeException(e);
                                    }
                                })
                        .collect(groupingBy(SwaggerExampleHolder::getCode));

        // -------------------------- 콘텐츠 세팅 코드별로 진행
        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private Example getSwaggerExample(String value, Error error) {
        Example example = new Example();
        com.gaebaljip.exceed.common.ApiResponse.CustomBody<String> errorResponse =
                new com.gaebaljip.exceed.common.ApiResponse.CustomBody<>(false, null, error);
        example.description(value);
        example.setValue(errorResponse);
        return example;
    }

    private void addExamplesToResponses(
            ApiResponses responses,
            Map<Integer, List<SwaggerExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
                (status, v) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    ApiResponse apiResponse = new ApiResponse();
                    v.forEach(
                            swaggerExampleHolder ->
                                    mediaType.addExamples(
                                            swaggerExampleHolder.getName(),
                                            swaggerExampleHolder.getHolder()));
                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(status.toString(), apiResponse);
                });
    }

    private static List<String> getTags(HandlerMethod handlerMethod) {
        List<String> tags = new ArrayList<>();

        Tag[] methodTags = handlerMethod.getMethod().getAnnotationsByType(Tag.class);
        List<String> methodTagStrings = Arrays.stream(methodTags).map(Tag::name).toList();

        Tag[] classTags = handlerMethod.getClass().getAnnotationsByType(Tag.class);
        List<String> classTagStrings = Arrays.stream(classTags).map(Tag::name).toList();
        tags.addAll(methodTagStrings);
        tags.addAll(classTagStrings);
        return tags;
    }
}
