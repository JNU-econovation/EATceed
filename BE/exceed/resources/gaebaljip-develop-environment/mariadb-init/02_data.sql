use gaebaljip;

INSERT INTO MEMBER_TB (MEMBER_PK, CREATED_DATE, UPDATED_DATE, MEMBER_ACTIVITY, MEMBER_AGE, MEMBER_ETC, MEMBER_GENDER,
                       MEMBER_HEIGHT, MEMBER_WEIGHT, MEMBER_TARGET_WEIGHT, MEMBER_EMAIL, MEMBER_PASSWORD, MEMBER_ROLE, MEMBER_CHECKED)
VALUES (1, '2023-12-01 08:00:00', '2023-12-01 08:00:00', 'NOT_ACTIVE', 30, '비고 없음', 1, 175.0, 61.0, 66.0, 'abcd123!@gmail.com',
        '$2a$10$pljAKl0Ad3LnjQyQei.Yz.0Cfcn3Zv/xeBMDwUHDaUrfG8Wm57c56', 'MEMBER', true),
       (2, '2023-12-01 08:00:00', '2023-12-01 08:00:00', 'NOT_ACTIVE', 30, '비고 없음', 1, 175.0, 61.0, 66.0, 'abcd234@@gmail.com',
        '$2a$10$pljAKl0Ad3LnjQyQei.Yz.0Cfcn3Zv/xeBMDwUHDaUrfG8Wm57c56', 'MEMBER', true),
       (3, '2024-05-14 08:00:00', '2024-05-14 08:00:00', 'NOT_ACTIVE', 24, '비고 없음', 1, 175.0, 67.0, 70.0, 'wwns1411@naver.com',
        '$2a$10$yCTgvLMTXsLepju7jXYKu.O9bYEe.7G5FJNfNkdin8HiYYvCq82.6', 'MEMBER', true);

