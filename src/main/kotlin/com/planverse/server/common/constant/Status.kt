package com.planverse.server.common.constant

import org.springframework.http.HttpStatus

enum class StatusCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus,
) {
    SUCCESS("0000", "성공", HttpStatus.OK),

    // API
    BAD_REQUEST("1000", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_USE_EMAIL("1001", "이미 사용 중인 이메일 입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("1002", "찾을 수 없는 정보입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_SENT_EMAIL("1003", "이미 메일이 전송되었습니다. 스팸메일함 또는 메일주소를 확인하여주세요.", HttpStatus.BAD_REQUEST),
    CANNOT_SENT_EMAIL("1004", "메일 전송에 실패했습니다. 관리자에게 문의 바랍니다.", HttpStatus.BAD_REQUEST),
    CANNOT_FIND_EMAIL("1005", "인증요청되지 않은 이메일입니다.", HttpStatus.BAD_REQUEST),
    EXPIRED_AUTH_EMAIL_REQUEST("1006", "인증 요청이 만료되었습니다. 재시도 부탁드립니다.", HttpStatus.BAD_REQUEST),
    ALREADY_AUTH_EMAIL("1007", "이미 인증된 사용자 입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXPECT("1008", "예상결과과 다릅니다.", HttpStatus.BAD_REQUEST),

    NOT_EXISTS_REQUEST("2000", "찾을 수 없는 요청 정보입니다.", HttpStatus.NOT_FOUND),

    TEAM_NOT_FOUND("x011", "팀을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TEAM_MEMBER_NOT_FOUND("x012", "팀에 소속된 멤버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // AUTH
    NO_AUTHORITY("8000", "권한 정보가 없습니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("8001", "인증 정보가 없습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("8008", "신뢰할 수 없는 정보입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("8003", "만료된 로그인 정보입니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN("8004", "변조된 로그인 정보입니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    LOGIN_FAIL("8005", "로그인에 실패하였습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("8005", "사용자를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED),

    FAIL("9999", "오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    companion object {
        private val map = entries.associateBy { it.code }
        infix fun from(code: String) = map[code] ?: FAIL
        operator fun get(code: String) = map[code] ?: FAIL
    }
}