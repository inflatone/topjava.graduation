package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.AuthUser;
import ru.javaops.restaurantvoting.ValidationGroups;
import ru.javaops.restaurantvoting.model.Role;
import ru.javaops.restaurantvoting.model.User;
import ru.javaops.restaurantvoting.repository.UserRepository;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(AccountController.REST_URL)
@RequiredArgsConstructor
public class AccountController {
    public static final String REST_URL = "/rest/account";

    private final UserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @AuthenticationPrincipal AuthUser auth) {
        User updated = repository.updateAndReturn(auth.id(), user);
        auth.update(updated);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/register")
    public ResponseEntity<User> register(@Validated(ValidationGroups.Persist.class) @RequestBody User user) {
        user.setRoles(Set.of(Role.USER));
        var created = repository.save(user);
        var uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
