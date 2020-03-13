package ru.javaops.restaurantvoting.web.converter.assembler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.Restaurant;

@Component
@RequiredArgsConstructor
public class RestaurantResourceAssembler implements RepresentationModelAssembler<Restaurant, EntityModel<Restaurant>> {
    private final RepositoryEntityLinks links;

    @Override
    public EntityModel<Restaurant> toModel(Restaurant entity) {
        var to = new EntityModel<>(entity);
        to.add(links.linkToItemResource(Restaurant.class, entity.id()).withSelfRel());
        return to;
    }
}
