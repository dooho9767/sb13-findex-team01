package com.sb13.findex.indexinfo.exception;

import java.time.Instant;

import com.sb13.findex.indexinfo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception
    ) {
        String details = extractDetails(exception);

        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "잘못된 요청입니다.",
                details
        );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(
            BindException exception
    ) {
        String details = extractDetails(exception);

        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "잘못된 요청입니다.",
                details
        );
    }

    private String extractDetails(BindException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("유효하지 않은 요청입니다.");
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(
            HttpStatus status,
            String message,
            String details
    ) {
        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                status.value(),
                message,
                details
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}