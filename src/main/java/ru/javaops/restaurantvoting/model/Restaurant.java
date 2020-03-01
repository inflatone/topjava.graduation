package ru.javaops.restaurantvoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Restaurant extends BaseEntity {
    @NotBlank
    @Size(max = 128)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OrderBy("date DESC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<DailyMenu> dailyMenus;

    public Restaurant(Integer id) {
        super(id);
    }
}
