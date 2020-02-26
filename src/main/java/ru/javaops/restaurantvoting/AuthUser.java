package ru.javaops.restaurantvoting;

import lombok.Getter;
import org.springframework.lang.NonNull;
import ru.javaops.restaurantvoting.model.User;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    @NonNull
    private User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
