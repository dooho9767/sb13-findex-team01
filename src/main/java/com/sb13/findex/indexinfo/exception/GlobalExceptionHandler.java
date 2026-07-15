package com.sb13.findex.indexinfo.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(IndexInfoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIndexInfoNotFound(
            IndexInfoNotFoundException exception
    ) {
        return createErrorResponse(
                HttpStatus.NOT_FOUND,
                "지수 정보를 찾을 수 없습니다.",
                exception.getMessage()
        );
    }
    @ExceptionHandler(DuplicateIndexInfoException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateIndexInfo(
            DuplicateIndexInfoException exception
    ) {
        return createErrorResponse(
                HttpStatus.CONFLICT,
                "지수 정보 등록에 실패했습니다.",
                exception.getMessage()
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