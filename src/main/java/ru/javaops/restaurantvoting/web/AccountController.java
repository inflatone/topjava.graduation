package ru.javaops.restaurantvoting.web;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.restaurantvoting.AuthUser;
import ru.javaops.restaurantvoting.model.User;

@RestController
@RequestMapping("/rest/account")
public class AccountController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }
}
