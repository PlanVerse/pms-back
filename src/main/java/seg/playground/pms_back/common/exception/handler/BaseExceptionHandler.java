//package seg.playground.pms_back.common.exception.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import seg.playground.pms_back.common.exception.BaseException;
//import seg.playground.pms_back.common.exception.ErrorResponse;
//
//@Slf4j
//@RestControllerAdvice
//public class BaseExceptionHandler {
//
//    @ExceptionHandler({RuntimeException.class})
//    public ResponseEntity<Object> handleException(Exception e) {
//        //        return ResponseEntity
//        //                .internalServerError()
//        //                .body(new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
//        return new ResponseEntity<>(new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(BaseException.class)
//    public ResponseEntity<Object> handleBaseException(BaseException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                ex.getCode(),
//                "Error",
//                ex.getMessage()
//        );
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
//    }
//}