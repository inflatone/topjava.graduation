package ru.javaops.restaurantvoting.web.converter.assembler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.DailyMenu;
import ru.javaops.restaurantvoting.model.Dish;
import ru.javaops.restaurantvoting.model.Restaurant;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DailyMenuResourceAssembler implements RepresentationModelAssembler<DailyMenu, EntityModel<DailyMenuResourceAssembler.TodayMenu>> {
    private final RestaurantResourceAssembler restaurantResourceAssembler;

    private final DishResourceAssembler dishResourceAssembler;

    private final RepositoryEntityLinks links;

    @Override
    public EntityModel<TodayMenu> toModel(DailyMenu entity) {
        EntityModel<Restaurant> restaurant = restaurantResourceAssembler.toModel(entity.getRestaurant());
        CollectionModel<EntityModel<Dish>> dishes = dishResourceAssembler.toCollectionModel(entity.getDishes());
        var dailyMenuTo = new TodayMenu(entity.getDate(), restaurant, dishes);
        return new EntityModel<>(dailyMenuTo, links.linkToItemResource(DailyMenu.class, entity.id()));
    }

    @Getter
    public static class TodayMenu {
        private final LocalDate date;

        private final EntityModel<Restaurant> restaurant;

        private final Iterable<EntityModel<Dish>> dishes;

        public TodayMenu(LocalDate date, EntityModel<Restaurant> restaurant, Iterable<EntityModel<Dish>> dishes) {
            this.date = date;
            this.restaurant = restaurant;
            this.dishes = dishes;
        }
    }
}
