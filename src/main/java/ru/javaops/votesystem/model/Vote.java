package ru.javaops.votesystem.model;

import java.time.LocalDate;

public class Vote extends BaseEntity {
    private User user;

    private Restaurant restaurant;

    private LocalDate date;

}
