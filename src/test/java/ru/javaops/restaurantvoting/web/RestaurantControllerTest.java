package ru.javaops.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantRepository repository;

    @Autowired
    private VoteRepository voteRepository;

    @Test
    void doVote() throws Exception {
        Restaurant restaurant = repository.findAll().get(0);
        int restaurantId = restaurant.id();
        vote(restaurantId).andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(asIsoDate(LocalDate.now()))))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/votes/today")))
                .andExpect(jsonPath("$._links.restaurant.href", is("http://localhost/restaurants/" + restaurantId)))
                .andExpect(jsonPath("$._links.user.href", is("http://localhost/users/1")));

        Page<Vote> votes = voteRepository.findAllByUserId(1, Pageable.unpaged());
        assertEquals(1, votes.getSize());
        Vote vote = votes.iterator().next();
        assertEquals(LocalDate.now(), vote.getDate());
        assertEquals(restaurantId, vote.getRestaurant().getId());
    }

    @Test
    void voteAgain() throws Exception {
        LocalTime now = LocalTime.now();
        List<Restaurant> restaurants = repository.findAll();

        int restaurantId = restaurants.get(0).id();
        vote(restaurantId); // first vote

        int newRestaurantId = restaurants.get(1).id();
        ResultActions resultActions = vote(newRestaurantId);
        boolean afterLimit = now.isAfter(RestaurantController.VOTE_AGAIN_TIME_LIMIT);
        if (afterLimit) {
            resultActions.andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.message", is("Too late to change your mind")));
        } else {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.date", is(asIsoDate(LocalDate.now()))));
        }
        Page<Vote> votes = voteRepository.findAllByUserId(1, Pageable.unpaged());
        Vote vote = votes.iterator().next();
        assertEquals(afterLimit ? restaurantId : newRestaurantId, vote.getRestaurant().getId());
    }

    private ResultActions vote(int restaurantId) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post(format("%s/%d/votes", RestaurantController.URL, restaurantId))
                .accept(MediaTypes.HAL_JSON)
                .with(httpBasic("user@gmail.com", "password")))
                .andDo(print());
    }
}
