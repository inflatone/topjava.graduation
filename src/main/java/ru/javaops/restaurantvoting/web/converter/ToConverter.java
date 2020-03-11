package ru.javaops.restaurantvoting.web.converter;

import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.DailyMenu;
import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.to.DishTo;
import ru.javaops.restaurantvoting.to.MenuTo;
import ru.javaops.restaurantvoting.to.RestaurantTo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ToConverter {
    private final RepositoryEntityLinks repositoryEntityLinks;

    public MenuTo createTodayMenuTo(DailyMenu menu) {
        List<DishTo> dishes = StreamEx.of(menu.getDishes()).map(d -> {
            var dishTo = new DishTo(d.getName(), String.valueOf(d.getPrice()));
            dishTo.add(repositoryEntityLinks.linkToItemResource(Dish.class, d.id()).withSelfRel());
            return dishTo;
        }).toList();

        MenuTo to = new MenuTo(null, dishes);
        to.add(repositoryEntityLinks.linkToItemResource(DailyMenu.class, menu.id()));
        return to;
    }

    public RestaurantTo createRestaurantTo(int id, String name, MenuTo menu) {
        var to = new RestaurantTo(name, menu);
        to.add(repositoryEntityLinks.linkToItemResource(Restaurant.class, id).withSelfRel());
        return to;
    }

    public RestaurantTo createRestaurantTo(Restaurant restaurant, @Nullable DailyMenu dailyMenu) {
        MenuTo menu = dailyMenu == null ? null : createTodayMenuTo(dailyMenu);
        return createRestaurantTo(restaurant.id(), restaurant.getName(), menu);
    }
}
