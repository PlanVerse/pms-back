package seg.playground.pms_back.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import seg.playground.pms_back.common.exception.code.StatusCode;

@Getter
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private StatusCode status;
    private Integer httpStatus;

    public BaseException(StatusCode status) {
        super(status.getMessage());
        this.status = status;
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
    }

    public BaseException(StatusCode status, Integer httpStatus) {
        super(status.getMessage());
        this.status = status;
        this.httpStatus = httpStatus;
    }

    public BaseException(StatusCode status, HttpStatus httpStatus) {
        super(status.getMessage());
        this.status = status;
        this.httpStatus = httpStatus.value();
    }
}