package com.gaebaljip.exceed.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {

    private final List<String> tableNames = new ArrayList<>();

    @PersistenceContext private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void findDatabaseTableNames() {
        List<Object> tableInfos = entityManager.createNativeQuery("SHOW TABLES").getResultList();
        for (Object tableInfo : tableInfos) {
            String tableName = (String) tableInfo;
            tableNames.add(tableName);
        }
    }

    private void truncate() {
        entityManager.createNativeQuery("SET foreign_key_checks = 0").executeUpdate();
        for (String tableName : tableNames) {
            entityManager
                    .createNativeQuery(String.format("TRUNCATE TABLE %s", tableName))
                    .executeUpdate();
        }
        entityManager.createNativeQuery("SET foreign_key_checks = 1").executeUpdate();
    }

    @Transactional
    public void clear() {
        entityManager.clear();
        truncate();
    }
}
