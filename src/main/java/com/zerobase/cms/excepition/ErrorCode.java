package com.zerobase.cms.excepition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입 된 회원 입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "일치하는 회원이 없습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST, "잘못 된 인증 시도입니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST, "인증 시간이 만료 되었습니다."),

    // Login
    LOGIN_CHECK_FAIL(HttpStatus.BAD_REQUEST, "아이디나 패스워드를 확인해주세요."),

    ALREADY_VERIFY(HttpStatus.BAD_REQUEST, "이미 인증이 완료 되었습니다."),

    NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST, "잔액이 부족합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}