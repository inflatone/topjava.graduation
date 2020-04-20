package ru.javaops.restaurantvoting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javaops.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByUserIdAndDate(int userId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    Page<Vote> findAllByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Pageable page);

    Page<Vote> findAllByUserId(int userId, Pageable page);

    Page<Vote> findAllByRestaurantIdAndDate(int restaurantId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                            Pageable page);
}
