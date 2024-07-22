package com.gaebaljip.exceed.common;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.gaebaljip.exceed.config.QueryDslConfig;

@Sql("classpath:db/testData.sql")
@Import(QueryDslConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DatabaseTest extends ContainerTest {}
