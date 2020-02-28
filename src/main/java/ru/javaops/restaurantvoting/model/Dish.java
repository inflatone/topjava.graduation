package ru.javaops.restaurantvoting.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Dish extends BaseEntity {
    @NotBlank
    @Size(max = 128)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Range(min = 0, max = 5000)
    @Column(name = "price", nullable = false)
    private Integer price;
}
