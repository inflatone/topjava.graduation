package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import ru.javaops.restaurantvoting.model.DailyMenu;
import ru.javaops.restaurantvoting.repository.DailyMenuRepository;
import ru.javaops.restaurantvoting.repository.projection.DailyMenuContentIncludedProjection;

import java.net.URI;
import java.time.LocalDate;

@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(DailyMenuResourceController.URL)
public class DailyMenuResourceController {
    public static final String URL = "/rest/" + DailyMenuRepository.URL;

    private final RepositoryEntityLinks links;

    @GetMapping(value = "/today", produces = MediaTypes.HAL_JSON_VALUE)
    public View today() {
        URI url = links.linksToSearchResources(DailyMenu.class)
                .getRequiredLink(DailyMenuRepository.BY_DATE_PATH)
                .getTemplate()
                .expand(LocalDate.now(), DailyMenuContentIncludedProjection.CONTENT_INCL_PROJECTION_NAME);
        return new InternalResourceView(url.getPath() + '?' + url.getQuery());
    }
}
