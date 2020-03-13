package ru.javaops.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.restaurantvoting.model.Restaurant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo extends BaseTo {
    private String name;
}
