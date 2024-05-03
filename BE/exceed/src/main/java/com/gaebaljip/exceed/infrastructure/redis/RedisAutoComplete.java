package com.gaebaljip.exceed.infrastructure.redis;

import static com.gaebaljip.exceed.common.EatCeedStaticMessage.REDIS_AUTO_COMPLETE_KEY;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import io.lettuce.core.RedisClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
@ConditionalOnExpression("${ableAutoComplete:true}")
public class RedisAutoComplete implements ApplicationRunner {
    private final RedisUtils redisUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
        try {
            redisUtils.deleteData(REDIS_AUTO_COMPLETE_KEY); // 기존 데이터 클리어

            // CSV 파일에서 식품명 열 데이터 로드
            String csvPath = args.getSourceArgs()[0];
            try (CSVReader reader =
                    new CSVReaderBuilder(new FileReader(csvPath))
                            .withSkipLines(1) // 첫 번째 행(헤더) 건너뛰기
                            .build()) {
                List<String[]> allRows = reader.readAll();
                for (String[] row : allRows) {
                    if (row != null && row.length > 0 && !row[0].isEmpty()) {
                        String foodName = row[0].trim();
                        addFoodToAutocomplete(foodName);
                    }
                }
            } catch (IOException | CsvException e) {
                log.error("Failed to read CSV file: {}", e.getMessage());
            }
        } finally {
            redisClient.shutdown();
        }
    }

    private void addFoodToAutocomplete(String foodName) {
        // 음식 이름에 대한 모든 접두사를 Redis sorted set에 추가
        for (int i = 1; i <= foodName.length(); i++) {
            String prefix = foodName.substring(0, i);
            redisUtils.zAdd(REDIS_AUTO_COMPLETE_KEY, prefix, 0D);
        }
        redisUtils.zAdd(REDIS_AUTO_COMPLETE_KEY, foodName + "*", 0D); // 완전한 단어를 표시
    }
}
