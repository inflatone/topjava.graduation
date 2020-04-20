package ru.javaops.restaurantvoting.web.processor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.web.RestaurantController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RestaurantModelProcessor implements RepresentationModelProcessor<EntityModel<Restaurant>> {
    @Override
    public EntityModel<Restaurant> process(EntityModel<Restaurant> model) {
        var link = linkTo(methodOn(RestaurantController.class).postVote(model.getContent().id(), null));
        model.add(link.withRel("votes"));
        return model;
    }
}
