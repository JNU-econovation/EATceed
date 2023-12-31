drop table if exists MEAL_FOOD_TB;
drop table if exists MEAL_TB;
drop table if exists MEMBER_TB;
drop table if exists FOOD_TB;

create table MEMBER_TB
(
    MEMBER_PK             bigint auto_increment
        primary key,
    CREATED_DATE          datetime(6)  not null,
    UPDATED_DATE          datetime(6)  not null,
    MEMBER_ACTIVITY       varchar(255) not null,
    MEMBER_AGE            int          not null,
    MEMBER_ETC            varchar(255) not null,
    MEMBER_GENDER         tinyint(1)   not null,
    MEMBER_HEIGHT         double       not null,
    MEMBER_IDENTIFICATION varchar(255) not null,
    MEMBER_WEIGHT         double       not null
);

create table food_tb
(
    FOOD_PK            bigint auto_increment
        primary key,
    FOOD_CALORIE       double       not null,
    FOOD_CARBOHYDRATE  double       not null,
    FOOD_FAT           double       not null,
    FOOD_MAIN_CATEGORY varchar(255) not null,
    FOOD_NAME          varchar(255) not null,
    FOOD_PROTEIN       double       not null,
    FOOD_SERVING_SIZE  double       not null,
    FOOD_SUB_CATEGORY  varchar(255) not null
);

create table MEAL_TB
(
    MEAL_PK            bigint auto_increment
        primary key,
    CREATED_DATE       datetime(6)  not null,
    UPDATED_DATE       datetime(6)  not null,
    MEAL_TYPE          varchar(255) not null,
    MEAL_FOOD_MULTIPLE double       not null,
    MEMBER_FK          bigint       null,
    constraint FK5napxbpjxsev8lnx004fcrqrn
        foreign key (MEMBER_FK) references MEMBER_TB (MEMBER_PK)
);

create table MEAL_FOOD_TB
(
    MEAL_FOOD_PK bigint auto_increment
        primary key,
    CREATED_DATE datetime(6) not null,
    UPDATED_DATE datetime(6) not null,
    FOOD_FK      bigint      null,
    MEAL_FK      bigint      null,
    constraint FKdipb7hg6jdu5764nq3wcnviua
        foreign key (FOOD_FK) references FOOD_TB (FOOD_PK),
    constraint FKr2altmd95kev38n36mno92lsi
        foreign key (MEAL_FK) references MEAL_TB (MEAL_PK)
);



