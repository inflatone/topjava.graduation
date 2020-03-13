package ru.javaops.restaurantvoting.to;

import org.springframework.hateoas.EntityModel;

public abstract class BaseTo extends EntityModel<BaseTo> {

    @Override
    public BaseTo getContent() {
        return this;
    }
}
