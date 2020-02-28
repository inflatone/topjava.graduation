package ru.javaops.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @RestResource(rel = "by-email", path = "by-email")
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    @RestResource(rel = "by-lastname", path = "by-lastname")
    List<User> findByLastNameContainingIgnoreCase(String lastName);

//    @Query("UPDATE User u SET u.email=LOWER(:email), u.firstName=:firstName, u.lastName=:lastName, u.password=:password WHERE u.id=:id")
//    void update(int id, User user);

    @Modifying
    @Query("UPDATE User u SET u.email=:#{#user.email}, u.firstName=:#{#user.firstName}, u.lastName=:#{#user.lastName} WHERE u.id=:id")
    void update(@Param("id") int id, @Param("user") User user);

    default User updateAndReturn(int id, User user) {
        update(id, user);
        return findById(id).get();
    }
}
