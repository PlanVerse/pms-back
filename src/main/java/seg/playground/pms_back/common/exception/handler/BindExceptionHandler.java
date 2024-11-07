package seg.playground.pms_back.common.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seg.playground.pms_back.common.util.RestUtil;

@Slf4j
@RestControllerAdvice
public class BindExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException e) {
        List<BindResponse.ErrorField> errorFields = new ArrayList<>();

        Map<String, List<FieldError>> errorGroup = e.getFieldErrors().stream().collect(Collectors.groupingBy(FieldError::getField));
        for (Map.Entry<String, List<FieldError>> entry : errorGroup.entrySet()) {
            errorFields.add(new BindResponse.ErrorField(entry.getKey(), entry.getValue().get(0).getRejectedValue(), entry.getValue().stream().map(FieldError::getDefaultMessage).toList()));
        }
        log.debug("errorFields \n{}\n", RestUtil.getJsonToString(errorFields));

        return ResponseEntity
                .badRequest()
                .body(new BindResponse(HttpStatus.BAD_REQUEST.value(), errorFields));
    }

    @Getter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class BindResponse {

        private Integer code;
        private List<ErrorField> errors;

        @Getter
        @ToString
        @AllArgsConstructor
        public static class ErrorField {

            private Object field;
            private Object value;
            private List<String> message;
        }
    }
}