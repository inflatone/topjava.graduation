package ru.javaops.restaurantvoting.web.processor;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.web.DailyMenuResourceController;
import ru.javaops.restaurantvoting.web.converter.assembler.DailyMenuResourceAssembler.TodayMenu;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DailyMenuProcessor implements RepresentationModelProcessor<CollectionModel<TodayMenu>> {
    @Override
    public CollectionModel<TodayMenu> process(CollectionModel<TodayMenu> model) {
        model.add(linkTo(methodOn(DailyMenuResourceController.class).today()).withRel("today"));
        return model;
    }
}
