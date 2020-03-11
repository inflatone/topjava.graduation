package ru.javaops.restaurantvoting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "daily_menu", uniqueConstraints =
        {@UniqueConstraint(name = DailyMenu.UNIQUE_RESTAURANT_DATE_INDEX, columnNames = {"restaurant_id", "date"})})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class DailyMenu extends BaseEntity {
    public static final String UNIQUE_RESTAURANT_DATE_INDEX = "daily_menu_unique_restaurant_date_idx";

    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @RestResource(path = "dishes", rel = "dishes")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "daily_menu_dishes", joinColumns = @JoinColumn(name = "daily_menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes;

}
