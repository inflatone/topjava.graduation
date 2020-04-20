package ru.javaops.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.model.User;
import ru.javaops.restaurantvoting.repository.UserRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class AccountControllerTest extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    @Test
    void register() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(AccountController.REST_URL + "/register")
                .accept(MediaTypes.HAL_JSON)
                .content(userAsJson(newUser()))
                .contentType(MediaType.APPLICATION_JSON));
        User expected = findLastAddedUser();
        resultActions
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/account"))
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expected.getLastName())));
    }

    @Test
    void update() throws Exception {
        User newUser = newUser();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .put(AccountController.REST_URL)
                .content(userAsJson(newUser))
                .contentType(MediaType.APPLICATION_JSON)
                .with(httpBasic("user@gmail.com", "password")));

        assertTrue(userRepository.findByEmailIgnoreCase("user@gmail.com").isEmpty());
        User expected = userRepository.findByEmailIgnoreCase(newUser.getEmail()).orElseThrow();
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(expected.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expected.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expected.getLastName())));
    }

    private User findLastAddedUser() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).iterator().next();
    }

    private static User newUser() {
        return new User("newemail@mail.ru", "newFirstName", "newLastName", "newPassword", null);
    }

    private static String userAsJson(User user) {
        return "{\n" +
                "  \"email\": \"" + user.getEmail() + "\",\n" +
                "  \"firstName\": \"" + user.getFirstName() + "\",\n" +
                "  \"lastName\": \"" + user.getLastName() + "\",\n" +
                "  \"password\": \"" + user.getPassword() + "\"\n" +
                "}";
    }
}
