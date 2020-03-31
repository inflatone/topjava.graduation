package ru.javaops.restaurantvoting.util;

import ru.javaops.restaurantvoting.model.BaseEntity;

public class ValidationUtil {
    // http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;
        while (null != (cause = result.getCause()) && result != cause) {
            result = cause;
        }
        return result;
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        // conservative when you reply, but accept liberally
        // http://stackoverflow.com/a/32728226/548473
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.id() != id) {
            throw new IllegalArgumentException(entity + " must has id=" + id);
        }
    }
}
