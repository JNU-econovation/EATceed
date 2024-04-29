package com.gaebaljip.exceed.common;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(DatabaseClearExtension.class)
@ExtendWith(RestDocumentationExtension.class)
@Sql("classpath:db/testData.sql")
@ActiveProfiles("test")
@Slf4j
public abstract class IntegrationTest {

    @Autowired protected MockMvc mockMvc;

    @Autowired protected ObjectMapper om;

    @Autowired private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                        .alwaysDo(print())
                        .apply(documentationConfiguration(restDocumentation))
                        .build();
    }
}
