CREATE TABLE `meetravel`.`chat_room` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `CREATED_AT` DATETIME NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `meetravel`.`user_chat_room` (
    `ID` BIGINT NOT NULL AUTO_INCREMENT,
    `USER_ID` VARCHAR(25) NOT NULL,
    `CHAT_ROOM_ID` BIGINT NOT NULL,
    `JOINED_AT` DATETIME NOT NULL,
    `LEAVE_AT` DATETIME NULL,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `meetravel`.`chat_message` (
    `ID` VARCHAR(36) NOT NULL,
    `USER_ID` VARCHAR(25) NOT NULL,
    `CHAT_ROOM_ID` BIGINT NOT NULL,
    `MESSAGE` TEXT NOT NULL,
    `MESSAGE_TYPE` VARCHAR(5) NOT NULL,
    `CREATED_AT` DATETIME NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;