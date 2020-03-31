package ru.javaops.restaurantvoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.model.Lunch;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@RepositoryRestResource(path = LunchRepository.URL)
public interface LunchRepository extends JpaRepository<Lunch, Integer> {
    String URL = "lunches";

    String BY_DATE_PATH = "by-date";

    @RestResource(rel = "by-date", path = BY_DATE_PATH)
    @EntityGraph(attributePaths = {"restaurant", "dishes"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Lunch> findLunchesByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);
}
