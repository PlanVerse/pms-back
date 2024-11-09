package seg.playground.pms_back.common.exception.code;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(true, "0000", "성공"),

    // 일반적 오류
    UNKNOWN_ERROR(false, "1000", "알수없는 오류가 발생하였습니다. 해당 오류가 지속된다면 관리자에게 문의 바랍니다."),
    BAD_REQUEST(false, "1001", "잘못된 요청입니다."),
    NOT_EXPECT(false, "1002", "예상결과과 다릅니다."),

    // 토근 및 권한 오류
    NO_AUTHORITY(false, "2000", "권한 정보가 없습니다."),
    NO_AUTHORITY_TMP_REDIRECT(false, "2001", "권한 정보가 없습니다."),
    UNAUTHORIZED(false, "2002", "인증 정보가 없습니다."),
    ALREADY_USE_EMAIL(false, "2003", "이미 사용 중인 이메일 입니다."),
    INVALID_TOKEN(false, "2004", "신뢰할 수 없는 토큰입니다."),
    EXPIRED_TOKEN(false, "2005", "존재하지 않거나 만료된 로그인 정보입니다. 다시 로그인해주세요."),
    UNSUPPORTED_TOKEN(false, "2006", "변조된 로그인 정보입니다. 다시 로그인해주세요."),
    ;

    private final Boolean success;
    private final String code;
    private final String message;

    StatusCode(Boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}