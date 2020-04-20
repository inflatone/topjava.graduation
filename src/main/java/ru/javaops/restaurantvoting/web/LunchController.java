package ru.javaops.restaurantvoting.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import ru.javaops.restaurantvoting.model.Lunch;
import ru.javaops.restaurantvoting.repository.LunchRepository;
import ru.javaops.restaurantvoting.repository.projection.LunchWithDetailsProjection;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDate;

@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(LunchController.URL)
public class LunchController {
    public static final String URL = '/' + LunchRepository.URL;

    private final RepositoryEntityLinks links;

    @GetMapping(value = "/today", produces = MediaTypes.HAL_JSON_VALUE)
    public View today(Pageable page, HttpServletRequest request) {
        URI url = links.linksToSearchResources(Lunch.class)
                .getRequiredLink(LunchRepository.BY_DATE_PATH)
                .getTemplate()
                .expand(LocalDate.now(), page.getPageNumber(), page.getPageSize(), page.getSort(),
                        LunchWithDetailsProjection.CONTENT_INCL_PROJECTION_NAME);
        URI relativizedURI = URI.create(request.getContextPath())
                .relativize(URI.create(url.getPath() + '?' + url.getQuery()));
        return new InternalResourceView('/' + relativizedURI.toString());
    }
}
