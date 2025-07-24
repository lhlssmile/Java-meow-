package lhl.hana.xk.review_multi.util;

import lombok.Getter;

/**
 * 这是一个自定义的响应类型
 * 返回信息? 响应code , 响应信息?
 * 泛型定义 通用
 */
@Getter
public class ResponseMineUtil<T> {

    private int code;

    private String message;

    private T data;

    private ResponseMineUtil(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static <T> ResponseMineUtil<T> success(T data) {
        return new ResponseMineUtil<>(200,"success", data);
    }
    public static <T> ResponseMineUtil<T> error(int code, String message) {
        return new ResponseMineUtil<>(code, message, null);
    }
}
