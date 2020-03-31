package ru.javaops.restaurantvoting.web.processor;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.Lunch;
import ru.javaops.restaurantvoting.web.LunchController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LunchCollectionModelProcessor implements RepresentationModelProcessor<CollectionModel<Lunch>> {
    @Override
    public CollectionModel<Lunch> process(CollectionModel<Lunch> model) {
        model.add(linkTo(methodOn(LunchController.class).today()).withRel("today"));
        return model;
    }
}