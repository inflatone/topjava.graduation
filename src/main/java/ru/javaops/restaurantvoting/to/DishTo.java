package ru.javaops.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.restaurantvoting.model.Dish;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishTo extends BaseTo {
    private String name;

    private String price;
}
