package ru.javaops.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuTo extends BaseTo {
    private RestaurantTo restaurant;

    private LocalDate date;

    private Iterable<DishTo> dishes;
}
