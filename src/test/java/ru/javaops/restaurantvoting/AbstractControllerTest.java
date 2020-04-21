package ru.javaops.restaurantvoting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected String asIsoDate(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }
}
