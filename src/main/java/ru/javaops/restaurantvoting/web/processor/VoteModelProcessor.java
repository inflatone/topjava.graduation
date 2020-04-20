package ru.javaops.restaurantvoting.web.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import ru.javaops.restaurantvoting.model.Restaurant;
import ru.javaops.restaurantvoting.model.User;
import ru.javaops.restaurantvoting.model.Vote;
import ru.javaops.restaurantvoting.web.VoteController;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class VoteModelProcessor implements RepresentationModelProcessor<EntityModel<Vote>> {
    private final EntityLinks entityLinks;

    @Override
    public EntityModel<Vote> process(EntityModel<Vote> model) {
        var entity = model.getContent();
        if (LocalDate.now().equals(entity.getDate())) {
            model.add(getLinkToCurrent().withSelfRel());
        }
        model.add(entityLinks.linkToItemResource(Restaurant.class, entity.getRestaurant().getId()).withRel("restaurant"));
        model.add(entityLinks.linkToItemResource(User.class, entity.getUser().getId()).withRel("user"));
        return model;
    }

    public WebMvcLinkBuilder getLinkToCurrent() {
        return linkTo(methodOn(VoteController.class).today(null));
    }
}
