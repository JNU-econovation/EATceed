use gaebaljip;

DELETE FROM MEAL_FOOD_TB;
DELETE FROM MEAL_TB;
DELETE FROM FOOD_TB;
DELETE FROM MEMBER_TB;

INSERT INTO MEMBER_TB (MEMBER_PK, CREATED_DATE, UPDATED_DATE, MEMBER_ACTIVITY, MEMBER_AGE, MEMBER_ETC, MEMBER_GENDER,
                       MEMBER_HEIGHT, MEMBER_EMAIL, MEMBER_PASSWORD, MEMBER_ROLE, MEMBER_CHECKED, MEMBER_WEIGHT)
VALUES (1, '2023-12-01 08:00:00', '2023-12-01 08:00:00', 'NOT_ACTIVE', 30, '비고 없음', 1, 175.0, 'abcd1111!@gmail.com',
        'abcd1234@', 'MEMBER', true,70.0);

INSERT INTO FOOD_TB (FOOD_PK, FOOD_CALORIE, FOOD_CARBOHYDRATE, FOOD_FAT, FOOD_MAIN_CATEGORY, FOOD_NAME, FOOD_PROTEIN,
                     FOOD_SERVING_SIZE, FOOD_SUB_CATEGORY)
VALUES (1, 200.0, 30.0, 10.0, '과일', '사과', 2.0, 120.0, '사과'),
       (2, 300.0, 40.0, 20.0, '과일', '바나나', 3.0, 120.0, '바나나'),
       (3, 400.0, 50.0, 30.0, '과일', '포도', 4.0, 120.0, '포도'),
       (4, 500.0, 60.0, 40.0, '과일', '수박', 5.0, 120.0, '수박'),
       (5, 600.0, 70.0, 50.0, '과일', '딸기', 6.0, 120.0, '딸기'),
       (6, 700.0, 80.0, 60.0, '과일', '복숭아', 7.0, 120.0, '복숭아'),
       (7, 800.0, 90.0, 70.0, '과일', '오렌지', 8.0, 120.0, '오렌지'),
       (8, 900.0, 10.0, 80.0, '과일', '키위', 9.0, 120.0, '키위'),
       (9, 1000.0, 20.0, 90.0, '과일', '체리', 10.0, 120.0, '체리'),
       (10, 1100.0, 30.0, 100.0, '과일', '레몬', 11.0, 120.0, '레몬'),
       (11, 1200.0, 40.0, 110.0, '과일', '자몽', 12.0, 120.0, '자몽'),
       (12, 1300.0, 50.0, 120.0, '과일', '망고', 13.0, 120.0, '망고'),
       (13, 1400.0, 60.0, 130.0, '과일', '파인애플', 14.0, 120.0, '파인애플'),
       (14, 1500.0, 70.0, 140.0, '과일', '아보카도', 15.0, 120.0, '아보카도'),
       (15, 1600.0, 80.0, 150.0, '과일', '망고스틴', 16.0, 120.0, '망고스틴'),
       (16, 1700.0, 90.0, 160.0, '과일', '파파야', 17.0, 120.0, '파파야'),
       (17, 1800.0, 10.0, 170.0, '과일', '블루베리', 18.0, 120.0, '블루베리'),
       (18, 1900.0, 20.0, 180.0, '과일', '라즈베리', 19.0, 120.0, '라즈베리'),
       (19, 2000.0, 30.0, 190.0, '과일', '블랙베리', 20.0, 120.0, '블랙베리'),
       (20, 200.0, 245.0, 1.3, '구이류', '가자미구이', 37.4, 10.0, '어패류구이'),
       (21, 200.0, 275.0, 1.2, '구이류', '갈치구이', 43.1, 11.0, '어패류구이'),
       (22, 200.0, 551.0, 0.4, '구이류', '고등어구이', 47.7, 40.0, '어패류구이'),
       (23, 200.0, 679.0, 7.6, '구이류', '고등어석쇠구이', 46.7, 51.4, '어패류구이'),
       (24, 150.0, 291.0, 0.1, '구이류', '굴비구이', 28.0, 19.9, '어패류구이'),
       (25, 200.0, 604.0, 4.3, '구이류', '꽁치구이', 49.4, 43.4, '어패류구이'),
       (26, 250.0, 397.0, 2.1, '구이류', '도미구이', 61.1, 16.5, '어패류구이'),
       (27, 70.0, 261.0, 17.6, '구이류', '뱅어포구이', 24.1, 10.6, '어패류구이'),
       (28, 250.0, 488.0, 0.2, '구이류', '병어구이', 54.6, 30.8, '어패류구이'),
       (29, 100.0, 296.54, 11.6, '구이류', '붕장어소금구이', 26.0, 16.2, '어패류구이'),
       (30, 200.0, 355.71, 8.48, '구이류', '삼치구이', 37.83, 18.01, '어패류구이'),
       (31, 180.0, 260.0, 5.1, '구이류', '새우구이', 42.1, 8.0, '어패류구이'),
       (32, 150.0, 433.35, 8.8, '구이류', '양념장어구이', 30.77, 30.56, '어패류구이'),
       (33, 250.0, 494.0, 3.5, '구이류', '임연수구이', 53.8, 29.4, '어패류구이'),
       (34, 180.0, 460.0, 36.4, '구이류', '조기구이', 34.8, 19.5, '어패류구이'),
       (35, 5.0, 23.0, 1.4, '구이류', '조미김', 1.0, 1.5, '어패류구이'),
       (36, 70.0, 238.0, 37.4, '구이류', '쥐포구이', 21.7, 0.2, '어패류구이'),
       (37, 200.0, 326.21, 2.3, '구이류', '짚불구이곰장어', 42.7, 16.2, '어패류구이'),
       (38, 180.0, 346.0, 2.5, '구이류', '참치머리구이', 47.8, 16.2, '어패류구이'),
       (39, 180.0, 234.0, 18.8, '구이류', '키조개구이', 31.4, 3.7, '어패류구이'),
       (40, 200.0, 437.57, 26.9, '구이류', '황태구이', 47.8, 15.4, '어패류구이'),
       (41, 300.0, 740.0, 10.6, '구이류', '간장양념닭다리구이', 68.9, 46.9, '육류구이'),
       (42, 100.0, 142.0, 3.0, '구이류', '그레이프 소스 스테이크', 10.0, 10.0, '육류구이'),
       (43, 500.0, 368.8, 39.7, '구이류', '꿩불고기', 33.5, 8.5, '육류구이'),
       (44, 170.0, 416.0, 17.6, '구이류', '닭고기대파꼬치구이', 30.3, 25.0, '육류구이'),
       (45, 340.0, 872.0, 26.5, '구이류', '닭구이', 89.1, 45.5, '육류구이'),
       (46, 70.0, 177.51, 12.9, '구이류', '닭꼬치', 12.35, 7.9, '육류구이'),
       (47, 220.0, 431.0, 21.3, '구이류', '닭발구이', 39.9, 20.7, '육류구이'),
       (48, 220.0, 431.0, 21.3, '구이류', '양구이', 39.9, 20.7, '육류구이'),
       (49, 220.0, 431.0, 21.3, '구이류', '소구이', 39.9, 20.7, '육류구이'),
       (50, 220.0, 431.0, 21.3, '구이류', '토끼구이', 39.9, 20.7, '육류구이'),
       (51, 220.0, 431.0, 21.3, '구이류', '멧돼지구이', 39.9, 20.7, '육류구이'),
       (52, 220.0, 431.0, 21.3, '구이류', '참새구이', 39.9, 20.7, '육류구이'),
       (53, 100.0, 143.0, 8.0, '구이류', '더블 체다 함박 스테이크', 12.0, 7.0, '육류구이'),
       (54, 100.0, 248.52, 7.6, '구이류', '돼지갈비', 19.95, 14.7, '육류구이'),
       (55, 170.0, 339.0, 2.6, '구이류', '돼지고기산적', 49.4, 14.6, '육류구이'),
       (56, 200.0, 449.0, 8.1, '구이류', '돼지불고기', 29.7, 33.1, '육류구이'),
       (57, 200.0, 388.0, 0.3, '구이류', '등심스테이크', 60.4, 16.1, '육류구이'),
       (58, 250.0, 762.99, 26.61, '구이류', '떡갈비', 43.08, 51.58, '육류구이'),
       (59, 160.0, 484.0, 5.2, '구이류', '런천미트구이', 28.1, 39.0, '육류구이'),
       (60, 100.0, 179.0, 9.0, '구이류', '레드 칠리 스테이크', 11.0, 11.0, '육류구이'),
       (61, 100.0, 128.0, 5.0, '구이류', '레드와인소스스테이크', 9.0, 8.0, '육류구이'),
       (62, 200.0, 805.0, 25.3, '구이류', '삼겹살고추장구이', 28.4, 65.7, '육류구이'),
       (63, 200.0, 933.0, 0.7, '구이류', '삼겹살구이', 45.1, 83.4, '육류구이'),
       (64, 300.0, 722.0, 4.8, '구이류', '소갈비구이', 77.7, 43.7, '육류구이');

INSERT INTO MEAL_TB (MEAL_PK, CREATED_DATE, UPDATED_DATE, MEAL_TYPE, MEAL_FOOD_MULTIPLE, MEMBER_FK)
VALUES (1, '2023-12-03 08:00:00', '2023-12-03 08:00:00', 'BREAKFAST', 1.0, 1),
       (2, '2023-12-03 13:00:00', '2023-12-03 13:00:00', 'LUNCH', 1.5, 1),
       (3, '2023-12-04 08:00:00', '2023-12-04 08:00:00', 'BREAKFAST', 1.0, 1),
       (4, '2023-12-04 13:00:00', '2023-12-04 13:00:00', 'LUNCH', 1.5, 1),
       (5, '2023-12-05 08:00:00', '2023-12-05 08:00:00', 'BREAKFAST', 1.0, 1),
       (6, '2023-12-05 13:00:00', '2023-12-05 13:00:00', 'LUNCH', 1.5, 1),
       (7, '2023-12-06 08:00:00', '2023-12-06 08:00:00', 'BREAKFAST', 1.0, 1),
       (8, '2023-12-06 13:00:00', '2023-12-06 13:00:00', 'LUNCH', 1.5, 1),
       (9, '2023-12-07 08:00:00', '2023-12-07 08:00:00', 'BREAKFAST', 1.0, 1),
       (10, '2023-12-07 13:00:00', '2023-12-07 13:00:00', 'LUNCH', 1.5, 1),
       (11, '2023-12-08 08:00:00', '2023-12-08 08:00:00', 'BREAKFAST', 1.0, 1),
       (12, '2023-12-08 13:00:00', '2023-12-08 13:00:00', 'LUNCH', 1.5, 1),
       (13, '2023-12-09 08:00:00', '2023-12-09 08:00:00', 'BREAKFAST', 1.0, 1),
       (14, '2023-12-09 13:00:00', '2023-12-09 13:00:00', 'LUNCH', 1.5, 1),
       (15, '2023-12-10 08:00:00', '2023-12-10 08:00:00', 'BREAKFAST', 1.0, 1),
       (16, '2023-12-10 13:00:00', '2023-12-10 13:00:00', 'LUNCH', 1.5, 1),
       (17, '2023-12-11 08:00:00', '2023-12-11 08:00:00', 'BREAKFAST', 1.0, 1),
       (18, '2023-12-11 13:00:00', '2023-12-11 13:00:00', 'LUNCH', 1.5, 1),
       (19, '2023-12-12 08:00:00', '2023-12-12 08:00:00', 'BREAKFAST', 1.0, 1),
       (20, '2023-12-12 13:00:00', '2023-12-12 13:00:00', 'LUNCH', 1.5, 1),
       (21, '2023-12-13 08:00:00', '2023-12-13 08:00:00', 'BREAKFAST', 1.0, 1),
       (22, '2023-12-13 13:00:00', '2023-12-13 13:00:00', 'LUNCH', 1.5, 1),
       (23, '2023-12-14 08:00:00', '2023-12-14 08:00:00', 'BREAKFAST', 1.0, 1),
       (24, '2023-12-14 13:00:00', '2023-12-14 13:00:00', 'LUNCH', 1.5, 1),
       (25, '2023-12-15 08:00:00', '2023-12-15 08:00:00', 'BREAKFAST', 1.0, 1),
       (26, '2023-12-15 13:00:00', '2023-12-15 13:00:00', 'LUNCH', 1.5, 1),
       (27, '2023-12-16 08:00:00', '2023-12-16 08:00:00', 'BREAKFAST', 1.0, 1),
       (28, '2023-12-16 13:00:00', '2023-12-16 13:00:00', 'LUNCH', 1.5, 1),
       (29, '2023-12-17 08:00:00', '2023-12-17 08:00:00', 'BREAKFAST', 1.0, 1),
       (30, '2023-12-17 13:00:00', '2023-12-17 13:00:00', 'LUNCH', 1.5, 1),
       (31, '2024-01-01 13:00:00', '2024-01-01 13:00:00', 'LUNCH', 1.0, 1),
       (32, '2024-01-01 19:00:00', '2024-01-01 19:00:00', 'DINNER', 1.0, 1),
       (33, '2024-01-02 12:00:00', '2024-01-12 08:00:00', 'SNACK', 1.0, 1),
       (34, '2024-01-02 13:00:00', '2024-01-02 13:00:00', 'LUNCH', 1.0, 1),
       (35, '2024-01-02 19:00:00', '2024-01-02 19:00:00', 'DINNER', 1.0, 1),
       (36, '2024-01-03 08:00:00', '2024-01-03 08:00:00', 'BREAKFAST', 1.0, 1),
       (37, '2024-01-03 15:00:00', '2024-01-03 15:00:00', 'SNACK', 1.0, 1),
       (38, '2024-01-03 19:00:00', '2024-01-03 19:00:00', 'DINNER', 1.0, 1),
       (39, '2024-01-04 08:00:00', '2024-01-04 08:00:00', 'BREAKFAST', 1.0, 1),
       (40, '2024-01-04 13:00:00', '2024-01-04 13:00:00', 'LUNCH', 1.0, 1),
       (41, '2024-01-04 19:00:00', '2024-01-04 19:00:00', 'DINNER', 1.0, 1),
       (42, '2024-01-05 08:00:00', '2024-01-05 08:00:00', 'BREAKFAST', 1.0, 1),
       (43, '2024-01-05 13:00:00', '2024-01-05 13:00:00', 'LUNCH', 1.0, 1);



INSERT INTO MEAL_FOOD_TB (MEAL_FOOD_PK, CREATED_DATE, UPDATED_DATE, FOOD_FK, MEAL_FK)
VALUES (1, '2023-12-03 08:00:00', '2023-12-03 08:00:00', 1, 1),
       (2, '2023-12-03 13:00:00', '2023-12-03 13:00:00', 2, 2),
       (3, '2023-12-04 08:00:00', '2023-12-04 08:00:00', 3, 3),
       (4, '2023-12-04 13:00:00', '2023-12-04 13:00:00', 4, 4),
       (5, '2023-12-05 08:00:00', '2023-12-05 08:00:00', 5, 5),
       (6, '2023-12-05 13:00:00', '2023-12-05 13:00:00', 6, 6),
       (7, '2023-12-06 08:00:00', '2023-12-06 08:00:00', 7, 7),
       (8, '2023-12-06 13:00:00', '2023-12-06 13:00:00', 8, 8),
       (9, '2023-12-07 08:00:00', '2023-12-07 08:00:00', 9, 9),
       (10, '2023-12-07 13:00:00', '2023-12-07 13:00:00', 10, 10),
       (11, '2023-12-08 08:00:00', '2023-12-08 08:00:00', 11, 11),
       (12, '2023-12-08 13:00:00', '2023-12-08 13:00:00', 12, 12),
       (13, '2023-12-09 08:00:00', '2023-12-09 08:00:00', 12, 13),
       (14, '2023-12-09 13:00:00', '2023-12-09 13:00:00', 1, 14),
       (15, '2023-12-10 08:00:00', '2023-12-10 08:00:00', 2, 15),
       (16, '2023-12-10 13:00:00', '2023-12-10 13:00:00', 3, 16),
       (17, '2023-12-11 08:00:00', '2023-12-11 08:00:00', 4, 17),
       (18, '2023-12-11 13:00:00', '2023-12-11 13:00:00', 5, 18),
       (19, '2023-12-12 08:00:00', '2023-12-12 08:00:00', 6, 19),
       (20, '2023-12-12 13:00:00', '2023-12-12 13:00:00', 7, 20),
       (21, '2023-12-13 08:00:00', '2023-12-13 08:00:00', 8, 21),
       (22, '2023-12-13 13:00:00', '2023-12-13 13:00:00', 9, 22),
       (23, '2023-12-14 08:00:00', '2023-12-14 08:00:00', 10, 23),
       (24, '2023-12-14 13:00:00', '2023-12-14 13:00:00', 11, 24),
       (25, '2023-12-15 08:00:00', '2023-12-15 08:00:00', 12, 25),
       (26, '2023-12-15 13:00:00', '2023-12-15 13:00:00', 12, 26),
       (27, '2023-12-16 08:00:00', '2023-12-16 08:00:00', 1, 27),
       (28, '2023-12-16 13:00:00', '2023-12-16 13:00:00', 2, 28),
       (29, '2023-12-17 08:00:00', '2023-12-17 08:00:00', 3, 29),
       (30, '2023-12-17 13:00:00', '2023-12-17 13:00:00', 4, 30),
       (31, '2024-01-01 13:00:00', '2024-01-01 13:00:00', 24, 31),
       (32, '2024-01-01 19:00:00', '2024-01-01 19:00:00', 23, 32),
       (33, '2024-01-02 12:00:00', '2024-01-12 08:00:00',  33, 33),
       (34, '2024-01-02 13:00:00', '2024-01-02 13:00:00', 32, 34),
       (35, '2024-01-02 19:00:00', '2024-01-02 19:00:00',  3, 35),
       (36, '2024-01-03 08:00:00', '2024-01-03 08:00:00',  42, 36),
       (37, '2024-01-03 15:00:00', '2024-01-03 15:00:00',  12, 37),
       (38, '2024-01-03 19:00:00', '2024-01-03 19:00:00',  27, 38),
       (39, '2024-01-04 08:00:00', '2024-01-04 08:00:00',  29, 39),
       (40, '2024-01-04 13:00:00', '2024-01-04 13:00:00',  30, 40),
       (41, '2024-01-04 19:00:00', '2024-01-04 19:00:00', 42, 41),
       (42, '2024-01-05 08:00:00', '2024-01-05 08:00:00',  44, 42),
       (43, '2024-01-05 13:00:00', '2024-01-05 13:00:00',  53, 43);







