package ru.javaops.restaurantvoting.web.processor;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.DailyMenu;
import ru.javaops.restaurantvoting.web.DailyMenuResourceController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DailyMenuCollectionModelProcessor implements RepresentationModelProcessor<CollectionModel<DailyMenu>> {
    @Override
    public CollectionModel<DailyMenu> process(CollectionModel<DailyMenu> model) {
        model.add(linkTo(methodOn(DailyMenuResourceController.class).today()).withRel("today"));
        return model;
    }
}