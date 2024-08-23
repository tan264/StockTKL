package com.example.stocktkl.exception;

import com.example.stocktkl.payload.response.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(ObjectNotValidException exp) {
        return ResponseEntity
                .badRequest()
                .body(exp.getErrorMessages());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleForbiddenException(
            AuthException ex,
            WebRequest request) {
        CustomErrorResponse exceptionResponse = new CustomErrorResponse(HttpStatus.UNAUTHORIZED,ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }


}
