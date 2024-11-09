package seg.playground.pms_back.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seg.playground.pms_back.common.exception.BaseException;
import seg.playground.pms_back.common.exception.code.BaseResponse;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleException(Exception e) {
        BaseResponse baseResponse = new BaseResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "RE",
                e.getMessage()
        );
        return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        BaseResponse baseResponse = new BaseResponse(
                ex.getCode(),
                "RE",
                ex.getMessage()
        );
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(ex.getCode()));
    }
}