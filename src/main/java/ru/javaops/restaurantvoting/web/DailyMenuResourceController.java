package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.javaops.restaurantvoting.model.DailyMenu;
import ru.javaops.restaurantvoting.repository.DailyMenuRepository;
import ru.javaops.restaurantvoting.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.web.converter.assembler.DailyMenuResourceAssembler;
import ru.javaops.restaurantvoting.web.converter.assembler.DailyMenuResourceAssembler.TodayMenu;

import java.time.LocalDate;
import java.util.List;

@ResponseBody
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(DailyMenuResourceController.URL)
public class DailyMenuResourceController {
    public static final String URL = "/rest/dailyMenus";

    private final RestaurantRepository restaurantRepository;

    private final DailyMenuRepository dailyMenuRepository;

    private final DailyMenuResourceAssembler menuResourceAssembler;

    @GetMapping(value = "/today", produces = MediaTypes.HAL_JSON_VALUE)
    public Iterable<EntityModel<TodayMenu>> today() {
        List<DailyMenu> menus = dailyMenuRepository.findDailyMenuByDate(LocalDate.now());
        return menuResourceAssembler.toCollectionModel(menus);
    }
}
