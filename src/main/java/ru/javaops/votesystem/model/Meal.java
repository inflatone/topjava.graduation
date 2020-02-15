package ru.javaops.votesystem.model;

public class Meal extends BaseNamedEntity {
    private int price;

    public Meal() {
    }

    public Meal(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
