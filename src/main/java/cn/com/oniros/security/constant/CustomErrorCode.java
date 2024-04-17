package cn.com.oniros.security.constant;

import cn.com.oniros.http.ErrorCode;

/**
 * cn.com.oniros.security.constant.CustomErrorCode
 *
 * @author Li Xiaoxu
 * 2024/4/14 15:50
 */
public enum CustomErrorCode implements ErrorCode {

    AUTH_ERROR(10001L, "Authentication Failed"),

    UNKNOWN_TOKEN(10002L, "Unknown Token");


    private final Long errorCode;

    private final String message;

    CustomErrorCode(Long errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public Long getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
