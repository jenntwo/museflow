package com.museflow.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
                        IllegalArgumentException exception,
                        HttpServletRequest request) {
                ApiErrorResponse response = new ApiErrorResponse(
                                Instant.now(),
                                400,
                                "Bad Request",
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
                        ResourceNotFoundException exception,
                        HttpServletRequest request) {
                ApiErrorResponse response = new ApiErrorResponse(
                                Instant.now(),
                                404,
                                "Not Found",
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationException(
                        MethodArgumentNotValidException exception,
                        HttpServletRequest request) {
                String message = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " +
                                                error.getDefaultMessage())
                                .collect(Collectors.joining("; "));

                ApiErrorResponse response = new ApiErrorResponse(
                                Instant.now(),
                                400,
                                "Bad Request",
                                message,
                                request.getRequestURI());

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

}