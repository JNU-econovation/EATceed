package com.gaebaljip.exceed.common.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.gaebaljip.exceed.adapter.out.jpa.food.FoodEntity;
import com.gaebaljip.exceed.application.port.out.food.FoodPort;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
@ConditionalOnExpression("${ableAutoComplete:true}")
@Profile("!prod")
public class MariaDBAutoComplete implements ApplicationRunner {

    private final FoodPort foodPort;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // CSV 파일에서 식품명 열 데이터 로드
        String csvPath = args.getSourceArgs()[0];
        try (CSVReader reader =
                new CSVReaderBuilder(new FileReader(csvPath))
                        .withSkipLines(1) // 첫 번째 행(헤더) 건너뛰기
                        .build()) {
            List<String[]> allRows = reader.readAll();
            List<FoodEntity> foodEntities =
                    allRows.stream()
                            .map(
                                    row ->
                                            FoodEntity.builder()
                                                    .calorie(Double.valueOf(row[1]))
                                                    .carbohydrate(Double.valueOf(row[2]))
                                                    .fat(Double.valueOf(row[3]))
                                                    .name(row[4])
                                                    .protein(Double.valueOf(row[5]))
                                                    .servingSize(Double.valueOf(row[6]))
                                                    .sugars(Double.valueOf(row[7]))
                                                    .dietaryFiber(Double.valueOf(row[8]))
                                                    .sodium(Double.valueOf(row[9]))
                                                    .build())
                            .toList();
            foodPort.saveAll(foodEntities);
        } catch (IOException | CsvException e) {
            log.error("Failed to read CSV file: {}", e.getMessage());
        }
    }
}
