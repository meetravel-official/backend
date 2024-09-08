package com.meetravel.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {

    ALREADY_EXISTS_USER_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 회원아이디입니다."),
    DATA_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "데이터가 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(HttpStatus.BAD_REQUEST, "HTTP 요청 본문 변환 중 에러가 발생했습니다."),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "메서드 파라미터가 유효하지 않습니다."),
    NO_SUCH_ELEMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "No Such Element Exception이 발생했습니다."),
    ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "ILLEGAL_ARGUMENT_EXCEPTION이 발생했습니다."),
    NULL_POINT_EXCEPTION(HttpStatus.BAD_REQUEST, "NULL_POINT_EXCEPTION이 발생했습니다."),
    INDEX_OUT_OF_BOUNDS_EXCEPTION(HttpStatus.BAD_REQUEST, "INDEX_OUT_OF_BOUNDS_EXCEPTION이 발생했습니다."),
    ARITHMETIC_EXCEPTION(HttpStatus.BAD_REQUEST, "ARITHMETIC_EXCEPTION이 발생했습니다."),
    MULTIPART_EXCEPTION(HttpStatus.BAD_REQUEST, "MULTIPART_EXCEPTION이 발생했씁니다."),
    DATABASE_EXCEPTION(HttpStatus.BAD_REQUEST, "DATABASE_EXCEPTION이 발생했습니다."),
    EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "EXCEPTION이 발생했습니다."),
    AUTHENTICATION_FAIL_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    TRAVEL_DEST_NOT_FOUND(HttpStatus.NOT_FOUND, "목록에 존재하지 않는 여행지입니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 권한은 목록에 없습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 데이터를 찾을 수 없습니다."),
    DATA_MAPPING_ERROR(HttpStatus.BAD_REQUEST, "데이터가 올바르게 매핑되지 않았습니다"),
    NOT_TEMPORARY_TOKEN_ALLOWED_URL_EXCEPTION(HttpStatus.UNAUTHORIZED, "임시 토큰으로 접근할 수 없는 URL입니다."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token 입니다."),
    USER_ALREADY_INSIDE_CHATROOM(HttpStatus.BAD_REQUEST, "해당 회원은 이미 채팅방에 참여하고 있습니다."),
    CHAT_ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 채팅방 고유번호입니다.")
    ;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    private final HttpStatus status;
    private final String message;
}
