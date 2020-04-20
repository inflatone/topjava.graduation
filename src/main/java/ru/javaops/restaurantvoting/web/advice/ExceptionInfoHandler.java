package ru.javaops.restaurantvoting.web.advice;

import one.util.streamex.StreamEx;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.javaops.restaurantvoting.util.exception.NotFoundException;

import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {
    @ExceptionHandler(NotFoundException.class)
    public Map<String, Object> applicationError(NotFoundException e) {
        return Map.of("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Map<String, Object> bindValidationError(Exception ex) {
        var result = ex instanceof BindException ? ((BindException) ex).getBindingResult()
                : ((MethodArgumentNotValidException) ex).getBindingResult();
        var errors = StreamEx.of(result.getFieldErrors())
                .toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage);
        return Map.of("message", "There're validation errors",
                "details", errors);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, Object> illegalStateError(IllegalStateException ex) {
        return Map.of("message", ex.getMessage());
    }
}
