package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.model.DailyMenu;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.repository.DailyMenuRepository;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.to.RestaurantTo;
import ru.javaops.restaurantvoting.web.converter.ToConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@RepositoryRestController
public class RestaurantController implements RepresentationModelProcessor<CollectionModel<Restaurant>> { // https://stackoverflow.com/a/24791083/10375242
    private final RestaurantRepository restaurantRepository;

    private final DailyMenuRepository dailyMenuRepository;

    private final ToConverter toConverter;

    @ResponseBody
    @GetMapping(value = RestaurantRepository.URL + "/withMenu", produces = MediaTypes.HAL_JSON_VALUE)
    public List<RestaurantTo> withMenus() {
        Map<Integer, DailyMenu> menuMap = StreamEx.of(dailyMenuRepository.findDailyMenuByDate(LocalDate.now()))
                .toMap(dm -> dm.getRestaurant().getId(), Function.identity());
        return StreamEx.of(restaurantRepository.findAll())
                .map(r -> toConverter.createRestaurantTo(r, menuMap.get(r.getId())))
                .toList();
    }

    @Override
    public CollectionModel<Restaurant> process(CollectionModel<Restaurant> model) {
        // https://github.com/spring-projects/spring-hateoas/issues/434#issuecomment-411770759
        model.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().path("/withMenu").build().toUriString(), "withMenu"));
        return model;
    }
}
