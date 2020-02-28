package ru.javaops.restaurantvoting;

import javax.validation.groups.Default;

public class ValidationGroups {
    // Validate only when user created
    public interface Persist extends Default {
    }
}