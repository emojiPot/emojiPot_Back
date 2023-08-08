package com.hanium.emoji_pot.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class ExceptionManager {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> AppExceptionHandler(AppException e) {
        log.error("에러가 발생했습니다. {}", e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(Response.error(new ErrorDto(e)));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> SQLExceptionHandler(SQLException e) {
        log.error("DB 관련 에러가 발생했습니다.");
        return ResponseEntity.status(ErrorCode.DATABASE_ERROR.getHttpStatus()).body(Response.error(new ErrorDto(e)));
    }

    public static ResponseEntity ifNullAndBlank() {
        ErrorCode e = ErrorCode.BLANK_NOT_ALLOWED;
        return ResponseEntity.status(e.getHttpStatus()).body(Response.error(new ErrorDto(e)));
    }

    public static void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        Response<ErrorDto> error = Response.error(new ErrorDto(errorCode.toString(), errorCode.getMessage()));
    }
}
