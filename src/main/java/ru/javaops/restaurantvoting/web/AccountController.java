package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.AuthUser;
import ru.javaops.restaurantvoting.ValidationGroups;
import ru.javaops.restaurantvoting.model.Role;
import ru.javaops.restaurantvoting.model.User;
import ru.javaops.restaurantvoting.repository.UserRepository;
import ru.javaops.restaurantvoting.util.ValidationUtil;

import javax.validation.Valid;
import java.util.Set;

@RepositoryRestController
@RequestMapping(value = AccountController.REST_URL, produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {
    public static final String REST_URL = "/rest/account";

    private final UserRepository repository;

    private final WebApplicationContext webApplicationContext;

    @GetMapping
    @ResponseBody
    public PersistentEntityResource get(@AuthenticationPrincipal AuthUser authUser, PersistentEntityResourceAssembler assembler) {
        return assembler.toFullResource(authUser.getUser());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PersistentEntityResource update(@Valid @RequestBody User user, PersistentEntityResourceAssembler assembler) {
        // @AuthenticationPrincipal not working with @RepositoryRestController
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User oldUser = auth.getUser();
        ValidationUtil.assureIdConsistent(user, oldUser.id());
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        return assembler.toFullResource(repository.save(user));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersistentEntityResource> register(@Validated(ValidationGroups.Persist.class) @RequestBody User user,
                                                             PersistentEntityResourceAssembler assembler) {
        user.setRoles(Set.of(Role.USER));
        var created = repository.save(user);
        var uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(assembler.toFullResource(created));
    }
}
