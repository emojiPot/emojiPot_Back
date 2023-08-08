package com.hanium.emoji_pot.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    private String message;

    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
