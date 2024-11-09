package seg.playground.pms_back.common.exception.code;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseResponse<T> {

    private final Boolean success;
    private final String code;
    private final String message;
    private final T data;

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(true, StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), data);
    }

    public static <T> BaseResponse<T> error(StatusCode status) {
        return new BaseResponse<>(false, status.getCode(), status.getMessage(), null);
    }

    public static <T> BaseResponse<T> error(StatusCode status, T data) {
        return new BaseResponse<>(false, status.getCode(), status.getMessage(), data);
    }
}