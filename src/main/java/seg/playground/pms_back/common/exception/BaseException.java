package seg.playground.pms_back.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final Integer code;
    private final String type = "RE";

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.code = status.value();
    }
}