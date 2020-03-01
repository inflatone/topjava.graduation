package ru.javaops.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.javaops.restaurantvoting.model.Dish;

@RepositoryRestResource(path = "dishes")
public interface DishRepository extends JpaRepository<Dish, Integer> {
}
