package seg.playground.pms_back.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseResponse {

    private Integer code;
    private String type;
    private String error;

    public BaseResponse(HttpStatus httpStatus, String type, String error) {
        this.code = httpStatus.value();
        this.error = error;
    }
}