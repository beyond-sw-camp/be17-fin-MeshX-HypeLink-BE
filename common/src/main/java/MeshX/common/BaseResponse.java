package MeshX.common;

import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private final T data;
    private final String message;

    private BaseResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> BaseResponse<T> of(T data) {
        return new BaseResponse<>(data, "");
    }

    public static <T> BaseResponse<T> of(T data, String message) {
        return new BaseResponse<>(data, message);
    }
}
