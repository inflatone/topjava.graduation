package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.restaurantvoting.AuthUser;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.Optional;


@RestController
@RequestMapping(value = VoteController.URL, produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class VoteController {
    public static final String URL = "/votes";

    private final VoteRepository repository;

    @GetMapping
    public PagedModel<EntityModel<Vote>> history(@AuthenticationPrincipal AuthUser auth, Pageable page,
                                                 PagedResourcesAssembler<Vote> pagedAssembler) {
        Page<Vote> votes = repository.findAllByUserId(auth.id(), page);
        return pagedAssembler.toModel(votes);
    }

    @GetMapping("/today")
    public ResponseEntity<?> today(@AuthenticationPrincipal AuthUser auth) {
        Optional<Vote> optionalVote = repository.findByUserIdAndDate(auth.id(), LocalDate.now());
        return optionalVote.map(v -> ResponseEntity.ok().body(new EntityModel<>(v)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
