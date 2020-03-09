package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.restaurantvoting.model.DailyMenu;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.repository.DailyMenuRepository;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.util.exception.NotFoundException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/rest/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {
    private final RestaurantRepository repository;

    private final DailyMenuRepository dailyMenuRepository;

    @GetMapping("/{id}/menu")
    public DailyMenu get(@PathVariable int id) {
        Optional<DailyMenu> restaurantMenu = dailyMenuRepository.findDailyMenuByDateAndRestaurantId(LocalDate.now(), id);
        return restaurantMenu.orElseThrow(() -> new NotFoundException("Today's menu is not present yet"));
    }

    @Transactional
    @PutMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public DailyMenu setMenu(@PathVariable int id, @Valid @RequestBody DailyMenu menu) {
        Optional<DailyMenu> optionalRestaurantMenu = dailyMenuRepository.findDailyMenuByDateAndRestaurantId(LocalDate.now(), id);
        if (optionalRestaurantMenu.isPresent()) {
            DailyMenu persistedMenu = optionalRestaurantMenu.get();
            persistedMenu.setDishes(menu.getDishes());
            return persistedMenu;
        }
        menu.setRestaurant(new Restaurant(id));
        return dailyMenuRepository.save(menu);
    }
}
