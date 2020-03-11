package ru.javaops.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo extends BaseTo {
    private String name;

    private MenuTo menu;
}
