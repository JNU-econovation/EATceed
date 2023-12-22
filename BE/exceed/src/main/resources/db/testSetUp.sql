SET REFERENTIAL_INTEGRITY False;
TRUNCATE TABLE MEMBER_TB;
TRUNCATE TABLE MEAL_FOOD_TB;
TRUNCATE TABLE MEAL_TB;
TRUNCATE TABLE FOOD_TB;
SET REFERENTIAL_INTEGRITY True;

INSERT INTO member_tb (created_date, updated_date, member_activity, member_age, member_etc, member_gender,
                       member_height, member_identification, member_weight)
VALUES ('2023-12-20 08:00:00', '2023-12-20 08:00:00', 'NOT_ACTIVE', 30, '비고 없음', 1, 175.0, 'user001', 70.0);

INSERT INTO food_tb (food_calorie, food_carbohydrate, food_fat, food_main_category, food_name, food_protein,
                     food_serving_size, food_sub_category)
VALUES (200.0, 30.0, 10.0, '과일', '사과', 2.0, 120.0, '사과'),
       (300.0, 40.0, 20.0, '과일', '바나나', 3.0, 120.0, '바나나'),
       (400.0, 50.0, 30.0, '과일', '포도', 4.0, 120.0, '포도'),
       (500.0, 60.0, 40.0, '과일', '수박', 5.0, 120.0, '수박'),
       (600.0, 70.0, 50.0, '과일', '딸기', 6.0, 120.0, '딸기'),
       (700.0, 80.0, 60.0, '과일', '복숭아', 7.0, 120.0, '복숭아'),
       (800.0, 90.0, 70.0, '과일', '오렌지', 8.0, 120.0, '오렌지'),
       (900.0, 10.0, 80.0, '과일', '키위', 9.0, 120.0, '키위'),
       (1000.0, 20.0, 90.0, '과일', '체리', 10.0, 120.0, '체리'),
       (1100.0, 30.0, 100.0, '과일', '레몬', 11.0, 120.0, '레몬'),
       (1200.0, 40.0, 110.0, '과일', '자몽', 12.0, 120.0, '자몽'),
       (1300.0, 50.0, 120.0, '과일', '망고', 13.0, 120.0, '망고');

INSERT INTO meal_tb (created_date, updated_date, meal_type, meal_food_multiple, member_fk)
VALUES ('2023-12-22 08:00:00', '2023-12-22 08:00:00', 'BREAKFAST', 1.0, 1),
       ('2023-12-22 13:00:00', '2023-12-22 13:00:00', 'LUNCH', 1.5, 1);

INSERT INTO meal_food_tb (created_date, updated_date, food_fk, meal_fk)
VALUES ('2023-12-22 08:00:00', '2023-12-22 08:00:00', 1, 1),
       ('2023-12-22 08:00:00', '2023-12-22 08:00:00', 2, 1),
       ('2023-12-22 13:00:00', '2023-12-22 13:00:00', 3, 2),
       ('2023-12-22 13:00:00', '2023-12-22 13:00:00', 4, 2);



