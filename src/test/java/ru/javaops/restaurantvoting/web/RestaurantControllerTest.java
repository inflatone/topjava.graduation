package ru.javaops.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantRepository repository;

    @Test
    void doVote() throws Exception {
        Restaurant restaurant = repository.findAll().get(0);
        ResultActions resultActions = vote(restaurant.id())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is(asIsoDate(LocalDate.now()))));
    }

    @Test
    void voteAgain() throws Exception {
        LocalTime now = LocalTime.now();
        List<Restaurant> restaurants = repository.findAll();

        vote(restaurants.get(0).id()); // first vote

        ResultActions resultActions = vote(restaurants.get(1).id());
        if (now.isAfter(RestaurantController.VOTE_AGAIN_TIME_LIMIT)) {
            resultActions.andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.message", is("Too late to change your mind")));
        } else {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.date", is(asIsoDate(LocalDate.now()))));
        }
    }

    private ResultActions vote(int restaurantId) throws Exception{
        return mockMvc.perform(MockMvcRequestBuilders
                .post(format("%s/%d/votes", RestaurantController.URL, restaurantId))
                .accept(MediaTypes.HAL_JSON)
                .with(httpBasic("user@gmail.com", "password")))
                .andDo(print());
    }
}
