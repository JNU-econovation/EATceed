package com.gaebaljip.exceed.common;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.gaebaljip.exceed.adapter.out.jpa.meal.MealPersistenceAdapter;
import com.gaebaljip.exceed.adapter.out.jpa.member.HistoryPersistenceAdapter;
import com.gaebaljip.exceed.application.service.member.MemberConverter;
import com.gaebaljip.exceed.config.QueryDslConfig;

@Sql("classpath:db/testData.sql")
@Import({
    QueryDslConfig.class,
    MemberConverter.class,
    HistoryPersistenceAdapter.class,
    MealPersistenceAdapter.class
})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DatabaseTest extends ContainerTest {}
