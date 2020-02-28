package ru.javaops.restaurantvoting.web;

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

import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {

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
}
