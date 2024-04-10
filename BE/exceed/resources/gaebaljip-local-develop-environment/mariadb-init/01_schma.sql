use gaebaljip;

CREATE TABLE `MEMBER_TB`
(
    `MEMBER_PK`       bigint(20)   NOT NULL AUTO_INCREMENT,
    `CREATED_DATE`    datetime(6)  NOT NULL,
    `UPDATED_DATE`    datetime(6)  NOT NULL,
    `MEMBER_ACTIVITY` varchar(255) DEFAULT NULL,
    `MEMBER_AGE`      int(11)      DEFAULT NULL,
    `MEMBER_ETC`      varchar(255) DEFAULT NULL,
    `MEMBER_GENDER`   tinyint      DEFAULT NULL,
    `MEMBER_HEIGHT`   double       DEFAULT NULL,
    `MEMBER_EMAIL`    varchar(255) NOT NULL UNIQUE,
    `MEMBER_PASSWORD` varchar(255) NOT NULL,
    `MEMBER_ROLE`     varchar(255) DEFAULT NULL,
    `MEMBER_CHECKED`  bit          NOT NULL,
    PRIMARY KEY (`MEMBER_PK`)
) ENGINE=InnoDB;

CREATE TABLE `WEIGHT_TB`
(
    `WEIGHT_PK`           bigint(20)   NOT NULL AUTO_INCREMENT,
    `CREATED_DATE`        datetime(6)  NOT NULL,
    `UPDATED_DATE`        datetime(6)  NOT NULL,
    `WEIGHT_WEIGHT`       double       DEFAULT NULL,
    `WEIGHT_TARGET_WEIGHT` double       DEFAULT NULL,
    `MEMBER_FK`           bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`WEIGHT_PK`),
    FOREIGN KEY (`MEMBER_FK`) REFERENCES `MEMBER_TB` (`MEMBER_PK`)
) ENGINE=InnoDB;


CREATE TABLE `FOOD_TB`
(
    `FOOD_PK`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `FOOD_CALORIE`       double       NOT NULL,
    `FOOD_CARBOHYDRATE`  double       NOT NULL,
    `FOOD_FAT`           double       NOT NULL,
    `FOOD_MAIN_CATEGORY` varchar(255) NOT NULL,
    `FOOD_NAME`          varchar(255) NOT NULL,
    `FOOD_PROTEIN`       double       NOT NULL,
    `FOOD_SERVING_SIZE`  double       NOT NULL,
    `FOOD_SUB_CATEGORY`  varchar(255) NOT NULL,
    PRIMARY KEY (`FOOD_PK`)
) ENGINE=InnoDB;


CREATE TABLE `MEAL_TB`
(
    `MEAL_PK`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `CREATED_DATE`       datetime(6)  NOT NULL,
    `UPDATED_DATE`       datetime(6)  NOT NULL,
    `MEAL_TYPE`          varchar(255) NOT NULL,
    `MEAL_FOOD_MULTIPLE` double       NOT NULL,
    `MEMBER_FK`          bigint(20) DEFAULT NULL,
    PRIMARY KEY (`MEAL_PK`),
    FOREIGN KEY (`MEMBER_FK`) REFERENCES `MEMBER_TB` (`MEMBER_PK`)
) ENGINE = InnoDB;

CREATE TABLE `MEAL_FOOD_TB`
(
    `MEAL_FOOD_PK` bigint(20)  NOT NULL AUTO_INCREMENT,
    `CREATED_DATE` datetime(6) NOT NULL,
    `UPDATED_DATE` datetime(6) NOT NULL,
    `FOOD_FK`      bigint(20) DEFAULT NULL,
    `MEAL_FK`      bigint(20) DEFAULT NULL,
    PRIMARY KEY (`MEAL_FOOD_PK`),
    FOREIGN KEY  (`FOOD_FK`) REFERENCES `FOOD_TB` (`FOOD_PK`),
    FOREIGN KEY  (`MEAL_FK`)REFERENCES `MEAL_TB` (`MEAL_PK`)
) ENGINE=InnoDB;

CREATE TABLE `MESSAGE_TB`
(
    `MESSAGE_PK`    bigint(20)  NOT NULL AUTO_INCREMENT,
    `CREATED_DATE`  datetime(6) NOT NULL,
    `MEMBER_QUESTION` text    NOT NULL,
    `CHAT_BOT_RESPONSE`  text    NOT NULL,
    `MEMBER_FK` bigint(20)  DEFAULT NULL,
    PRIMARY KEY (`MESSAGE_PK`),
    FOREIGN KEY (`MEMBER_FK`) REFERENCES MEMBER_TB(`MEMBER_PK`)
) ENGINE = InnoDB;