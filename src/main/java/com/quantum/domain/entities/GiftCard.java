package com.quantum.domain.entities;

import com.quantum.domain.interfaces.Item;

public class GiftCard implements Item {
    String Id;
    String name;
    String description;
    Double price;

    public GiftCard(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.Id = String.valueOf((this.name).hashCode());

    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Double getPrice() {
        return price;
    }
}
