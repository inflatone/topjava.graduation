package ru.javaops.restaurantvoting.web.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.javaops.restaurantvoting.model.Lunch;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.model.User;
import ru.javaops.restaurantvoting.util.ValidationUtil;

import java.util.Map;
import java.util.Optional;

@RestControllerAdvice(basePackageClasses = RepositoryRestExceptionHandler.class)
public class RestRepositoryErrorHandler {
    public static final Map.Entry<String, String> EXCEPTION_DUPLICATE_USER_EMAIL = Map.entry("email", "User with this email already exists");
    public static final Map.Entry<String, String> EXCEPTION_DUPLICATE_RESTAURANT_NAME = Map.entry("name", "Restaurant with this email already exists");
    public static final Map.Entry<String, String> EXCEPTION_DUPLICATE_LUNCH = Map.entry("restaurant", "Restaurant has already provided today's lunch");

    private static final Map<String, Map.Entry<String, String>> CONSTRAINS_I18N_MAP = Map.of(
            User.UNIQUE_EMAIL_INDEX, EXCEPTION_DUPLICATE_USER_EMAIL,
            Restaurant.UNIQUE_NAME_INDEX, EXCEPTION_DUPLICATE_RESTAURANT_NAME,
            Lunch.UNIQUE_RESTAURANT_DATE_INDEX, EXCEPTION_DUPLICATE_LUNCH
    );

    @ResponseStatus(HttpStatus.CONFLICT) // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, Object> conflict(DataIntegrityViolationException e) {
        String rootMessage = ValidationUtil.getRootCause(e).getMessage();
        Map.Entry<String, String> message = Map.entry("message", "There're validation errors");
        if (rootMessage != null) {
            String lowerCaseRootMessage = rootMessage.toLowerCase();
            Optional<Map.Entry<String, Map.Entry<String, String>>> messageOnConstrains = CONSTRAINS_I18N_MAP.entrySet().stream()
                    .filter(entry -> lowerCaseRootMessage.contains(entry.getKey()))
                    .findAny();
            if (messageOnConstrains.isPresent()) {
                return Map.ofEntries(message, Map.entry("details", messageOnConstrains.get().getValue()));
            }
        }
        return Map.ofEntries(message);
    }
}
