package ru.javaops.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.model.DailyMenu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RepositoryRestResource(path = "menus")
public interface DailyMenuRepository extends JpaRepository<DailyMenu, Integer> {

    @RestResource(rel = "by-date", path="by-date")
    List<DailyMenu> findDailyMenuByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @RestResource(exported = false)
    Optional<DailyMenu> findDailyMenuByDateAndRestaurantId(LocalDate date, int id);


}
