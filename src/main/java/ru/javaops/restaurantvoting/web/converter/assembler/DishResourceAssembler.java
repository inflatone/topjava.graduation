package ru.javaops.restaurantvoting.web.converter.assembler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.Dish;

@Component
@RequiredArgsConstructor
public class DishResourceAssembler implements RepresentationModelAssembler<Dish, EntityModel<Dish>> {
    private final RepositoryEntityLinks links;

    @Override
    public EntityModel<Dish> toModel(Dish entity) {
        var to = new EntityModel<>(entity);
        to.add(links.linkToItemResource(Dish.class, entity.id()).withSelfRel());
        return to;
    }
}
