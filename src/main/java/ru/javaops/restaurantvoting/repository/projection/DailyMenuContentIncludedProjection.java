package ru.javaops.restaurantvoting.repository.projection;

import org.springframework.data.rest.core.config.Projection;
import ru.javaops.restaurantvoting.model.DailyMenu;

import java.time.LocalDate;
import java.util.List;

@Projection(name = DailyMenuContentIncludedProjection.CONTENT_INCL_PROJECTION_NAME, types = DailyMenu.class)
public interface DailyMenuContentIncludedProjection {
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
