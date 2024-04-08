package com.gaebaljip.exceed.member.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class RandomIndexes {

    public static final int START = 0;
    public static final int END = 9;
    public static final int SIZE = 4;
    private final String indexes;

    public RandomIndexes() {
        List<Integer> randomIndexes = generateRandomIndexes(START, END, SIZE);
        this.indexes = convertToString(randomIndexes);
    }

    private String convertToString(List<Integer> randomIndexes) {
        StringBuilder sb = new StringBuilder();
        for (Integer randomIndex : randomIndexes) {
            sb.append(randomIndex);
        }
        return sb.toString();
    }

    private List<Integer> generateRandomIndexes(int start, int end, int size) {
        List<Integer> randomIndexes = new ArrayList<>();

        while (randomIndexes.size() < size) {
            int randomIndex = start + (int) (Math.random() * (end - start + 1));
            if (!randomIndexes.contains(randomIndex)) {
                randomIndexes.add(randomIndex);
            }
        }
        return randomIndexes;
    }
}
