package seg.playground.pms_back.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seg.playground.pms_back.common.exception.BaseException;
import seg.playground.pms_back.common.exception.code.BaseResponse;
import seg.playground.pms_back.common.exception.code.StatusCode;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Void>> handleRuntimeException(RuntimeException ignore) {
        return new ResponseEntity<>(BaseResponse.error(StatusCode.UNKNOWN_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleBaseException(BaseException be) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if (ObjectUtils.isNotEmpty(be.getHttpStatus())) {
            try {
                httpStatus = HttpStatus.valueOf(be.getHttpStatus());
            } catch (IllegalArgumentException ignore) {
            }
        }
        return new ResponseEntity<>(BaseResponse.error(be.getStatus()), httpStatus);
    }
}