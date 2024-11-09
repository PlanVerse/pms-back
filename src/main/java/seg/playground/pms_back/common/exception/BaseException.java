package seg.playground.pms_back.common.exception;

import lombok.Getter;
import seg.playground.pms_back.common.exception.code.StatusCode;

@Getter
public class BaseException extends RuntimeException {

    private final StatusCode status;

    public BaseException(StatusCode status) {
        super(status.getMessage());
        this.status = status;
    }
}