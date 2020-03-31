package ru.javaops.restaurantvoting.repository.projection;

import org.springframework.data.rest.core.config.Projection;
import ru.javaops.restaurantvoting.model.Lunch;

import java.time.LocalDate;
import java.util.List;

@Projection(name = LunchWithDetailsProjection.CONTENT_INCL_PROJECTION_NAME, types = Lunch.class)
public interface LunchWithDetailsProjection {
    String CONTENT_INCL_PROJECTION_NAME = "content-incl";

    LocalDate getDate();

    RestaurantDefaultProjection getRestaurant();

    List<DishBasicProjection> getDishes();

    interface RestaurantDefaultProjection {
        String getName();
//
//    // https://stackoverflow.com/a/45263603/10375242
//    @Value("#{@dailyMenuRepository.findTodayMenuByRestaurantId(target.id)}")
//    DailyMenu todayMenu();
    }

    interface DishBasicProjection {
        String getName();

        Integer getPrice();
    }

}
