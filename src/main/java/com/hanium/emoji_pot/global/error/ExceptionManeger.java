package com.hanium.emoji_pot.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManeger {

    @ExceptionHandler(ErrorController.class)
    public ResponseEntity<?> hospitalReviewAppExceptionHandler(ErrorController e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.error(e.getErrorCode().getMessage()));
    }
}
