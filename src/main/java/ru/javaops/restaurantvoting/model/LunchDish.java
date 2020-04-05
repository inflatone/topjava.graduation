package ru.javaops.restaurantvoting.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lunch_dishes")
public class LunchDish extends BaseEntity {
    @ManyToOne
    private Dish dish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lunch_id", nullable = false)
    private Lunch lunch;
}
