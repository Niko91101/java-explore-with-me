package ru.practicum.main.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.main.common.config.JacksonConfig.DATE_TIME_PATTERN;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private ApiError build(HttpStatus status, String reason, String message, List<String> errors) {
        return ApiError.builder()
                .status(status.name())
                .reason(reason)
                .message(message)
                .errors(errors == null ? List.of() : errors)
                .timestamp(LocalDateTime.now().format(FMT))
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public org.springframework.http.ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        log.warn("404 Not Found: {}", ex.getMessage());
        return new org.springframework.http.ResponseEntity<>(
                build(HttpStatus.NOT_FOUND, "The required object was not found.", ex.getMessage(), null),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public org.springframework.http.ResponseEntity<ApiError> handleConflict(ConflictException ex) {
        log.warn("409 Conflict: {}", ex.getMessage());
        return new org.springframework.http.ResponseEntity<>(
                build(HttpStatus.CONFLICT, "Integrity constraint has been violated.", ex.getMessage(), null),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public org.springframework.http.ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        log.warn("400 Bad Request: {}", ex.getMessage());
        return new org.springframework.http.ResponseEntity<>(
                build(HttpStatus.BAD_REQUEST, "Incorrectly made request.", ex.getMessage(), null),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public org.springframework.http.ResponseEntity<ApiError> handleValidation(Exception ex) {
        log.warn("400 Validation error: {}", ex.getMessage());
        return new org.springframework.http.ResponseEntity<>(
                build(HttpStatus.BAD_REQUEST, "Incorrectly made request.", ex.getMessage(), null),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public org.springframework.http.ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("409 Data integrity violation: {}", ex.getMostSpecificCause().getMessage());
        return new org.springframework.http.ResponseEntity<>(
                build(HttpStatus.CONFLICT, "Integrity constraint has been violated.",
                        ex.getMostSpecificCause().getMessage(), null),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public org.springframework.http.ResponseEntity<ApiError> handleOther(Exception ex) {
        log.error("500 Internal error", ex);
        return new org.springframework.http.ResponseEntity<>(
                build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error.", ex.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
