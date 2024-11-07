package seg.playground.pms_back.common.exception;

import lombok.Getter;

@Getter
public class BaseResponse {

    private final Integer code;
    private final String error;
    private final String message;

    public BaseResponse(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }
}