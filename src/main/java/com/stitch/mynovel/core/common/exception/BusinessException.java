package com.stitch.mynovel.core.common.exception;

import com.stitch.mynovel.core.common.constant.ErrorCodeEnum;
import lombok.Data;

/**
 * 业务异常
 */
@Data
public class BusinessException extends RuntimeException {
    private ErrorCodeEnum errorCodeEnum;

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

}
