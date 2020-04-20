package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.restaurantvoting.AuthUser;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(RestaurantController.URL)
public class RestaurantController {
    public static final String URL = '/' + RestaurantRepository.URL;

    private static final LocalTime VOTE_AGAIN_TIME_LIMIT = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    @Transactional
    @PostMapping(value = "{id}/votes", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<PersistentEntityResource> postVote(@PathVariable int id, PersistentEntityResourceAssembler assembler) {
        Restaurant restaurant = restaurantRepository.getOne(id);

        // @AuthenticationPrincipal not working with @RepositoryRestController
        AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Vote> optionalVote = voteRepository.findByUserIdAndDate(auth.id(), LocalDate.now());
        Vote vote = optionalVote.map(v -> {
            if (LocalTime.now().isAfter(VOTE_AGAIN_TIME_LIMIT)) {
                throw new IllegalStateException("Too late to change your mind");
            }
            v.setRestaurant(restaurant);
            return v;
        }).orElseGet(() -> new Vote(auth.getUser(), restaurant, LocalDate.now()));
        PersistentEntityResource persisted = assembler.toFullResource(voteRepository.save(vote));
        return (vote.isNew() ? ResponseEntity.created(persisted.getLink("self").get().toUri()) : ResponseEntity.ok())
                .body(persisted);
    }

    /**
     * @param id             restaurant id
     * @param pagedAssembler paged entity resource assembler
     * @return all today restaurant votes
     */
    @ResponseBody
    @GetMapping(value = "{id}/votes", produces = MediaTypes.HAL_JSON_VALUE)
    public PagedModel<EntityModel<Vote>> votes(@PathVariable int id, Pageable page,
                                               PagedResourcesAssembler<Vote> pagedAssembler) {
        Page<Vote> todayVotes = voteRepository.findAllByRestaurantIdAndDate(id, LocalDate.now(), page);
        return pagedAssembler.toModel(todayVotes);
    }
}
