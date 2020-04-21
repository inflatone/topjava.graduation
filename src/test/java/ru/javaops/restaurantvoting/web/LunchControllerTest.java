package ru.javaops.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.model.Lunch;
import ru.javaops.restaurantvoting.repository.LunchRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LunchControllerTest extends AbstractControllerTest {
    @Autowired
    private LunchRepository repository;

    @Test
    void today() throws Exception {
        String query = String.format("/lunches/search/by-date?date=%s&page=0&size=20&sort=UNSORTED&projection=content-incl",
                asIsoDate(LocalDate.now()));
        mockMvc.perform(MockMvcRequestBuilders
                .get(LunchController.URL + "/today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(query));
    }

    @Test
    void findByDate() throws Exception {
        String query = String.format("/lunches/search/by-date?date=%s&page=0&size=20&sort=UNSORTED&projection=content-incl",
                asIsoDate(LocalDate.now()));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get(query)
                .accept(MediaTypes.HAL_JSON)
                .with(httpBasic("admin@javaops.ru", "admin")))
                .andDo(print())
                .andExpect(status().isOk());

        List<Lunch> expected = repository.findLunchesByDate(LocalDate.now(), Pageable.unpaged()).getContent();

        resultActions
                .andExpect(jsonPath("$._embedded.lunches[0].date", is(asIsoDate(expected.get(0).getDate()))))
                .andExpect(jsonPath("$._embedded.lunches[1].lunchDishes[0].dish.price", is(expected.get(1).getLunchDishes().get(0).getDish().getPrice())))
                .andExpect(jsonPath("$._embedded.lunches[2].restaurant.name", is(expected.get(2).getRestaurant().getName())))
                .andExpect(jsonPath("$._embedded.lunches[0]._links.self.href", is("http://localhost/lunches/" + expected.get(0).getId())))
                .andExpect(jsonPath("$._embedded.lunches[1].lunchDishes[0]._links.self.href", is("http://localhost/lunchDishes/" + expected.get(1).getLunchDishes().get(0).getId())))
                .andExpect(jsonPath("$._embedded.lunches[2].restaurant._links.self.href", is("http://localhost/restaurants/" + expected.get(2).getRestaurant().getId())));

    }


}
