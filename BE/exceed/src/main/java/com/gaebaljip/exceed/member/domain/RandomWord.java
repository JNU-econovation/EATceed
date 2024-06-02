package com.gaebaljip.exceed.member.domain;

import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;

@Getter
public class RandomWord {

    private static final Integer LENGTH = 5;
    private final String word;

    public RandomWord() {
        this.word = generateRandomWord(LENGTH);
    }

    private String generateRandomWord(int wordLength) {
        StringBuilder word = new StringBuilder(wordLength);

        for (int i = 0; i < wordLength; i++) {
            // 'a' (97)부터 시작하여 알파벳 26글자 중 하나를 랜덤으로 선택
            char letter = (char) ('a' + ThreadLocalRandom.current().nextInt(26));
            word.append(letter);
        }

        return word.toString();
    }
}
