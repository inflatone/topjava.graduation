package ru.javaops.restaurantvoting.model;

import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = @UniqueConstraint(name = Restaurant.UNIQUE_NAME_INDEX, columnNames = {"name"}))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Restaurant extends BaseEntity {
    public static final String UNIQUE_NAME_INDEX = "restaurant_unique_name_idx";

    @NotBlank
    @Size(max = 128)
    @Column(name = "name", nullable = false)
    private String name;

    @OrderBy("date DESC")
    @RestResource(path = "history", rel = "history")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<DailyMenu> history;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @RestResource(path = "dishes", rel = "dishes")
    private List<Dish> dishes;

    public Restaurant(Integer id) {
        super(id);
    }
}
