package com.quantum.domain.entities;

import com.quantum.domain.interfaces.Item;
import com.quantum.domain.interfaces.Perishable;
import com.quantum.domain.interfaces.Shippable;

import java.time.LocalDate;

public class Produce implements Perishable, Shippable, Item {
    String Id;
    String name;
    String description;
    Double price;

    Double weight;
    LocalDate expiryDate;

    public Produce(String name, String description, Double price, Double weight, LocalDate expiryDate) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.Id = String.valueOf((this.expiryDate.toString() + this.name).hashCode());
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public int getType() {
        return 2;
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

    @Override
    public Double getWeight(){
        return weight;
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public Boolean isExpired() {
        return this.getExpiryDate().isBefore(LocalDate.now());
    }
}
