package ru.javaops.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.javaops.restaurantvoting.model.LunchDish;

@RepositoryRestResource(path = LunchDishRepository.URL)
public interface LunchDishRepository extends JpaRepository<LunchDish, Integer> {
    String URL = "lunchDishes";


}
