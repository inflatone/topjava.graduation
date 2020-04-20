package ru.javaops.restaurantvoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = Vote.UNIQUE_RESTAURANT_DATE_INDEX)})
public class Vote extends BaseEntity {
    public static final String UNIQUE_RESTAURANT_DATE_INDEX = "vote_unique_restaurant_date_idx";

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;
}
