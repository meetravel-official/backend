CREATE TABLE `meetravel`.`matching_application_form` (
    `MATCHING_FORM_ID` BIGINT NOT NULL AUTO_INCREMENT,
    `USER_ID` VARCHAR(25) NOT NULL,
    `CHAT_ROOM_ID` BIGINT NULL,
    `DURATION` VARCHAR(4) NOT NULL,
    `START_DT` DATE NOT NULL,
    `END_DT` DATE NOT NULL,
    `GROUP_SIZE` VARCHAR(2) NOT NULL,
    `GENDER_RATIO` VARCHAR(4) NOT NULL,
    `COST` VARCHAR(7) NOT NULL,
    `AREA_CODE` VARCHAR(10) NULL,
    `AREA_NAME` VARCHAR(255) NULL,
    `DETAIL_AREA_CODE` VARCHAR(10) NULL,
    `DETAIL_AREA_NAME` VARCHAR(255) NULL,
    `CREATE_DTM` DATETIME NOT NULL,
    `CREATOR` VARCHAR(25) NULL,
    `UPDATE_DTM` DATETIME NOT NULL,
    `UPDATER` VARCHAR(25) NULL,
    PRIMARY KEY (`MATCHING_FORM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `meetravel`.`travel_keyword` (
    `TRAVEL_KEYWORD_ID` BIGINT NOT NULL AUTO_INCREMENT,
    `TRAVEL_KEYWORD` VARCHAR(4) NOT NULL,
    `MATCHING_FORM_ID` BIGINT NOT NULL,
    `CREATE_DTM` DATETIME NOT NULL,
    `CREATOR` VARCHAR(25) NULL,
    `UPDATE_DTM` DATETIME NOT NULL,
    `UPDATER` VARCHAR(25) NULL,
    PRIMARY KEY (`TRAVEL_KEYWORD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;