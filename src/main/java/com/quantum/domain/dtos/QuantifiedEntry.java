package com.quantum.domain.dtos;

import com.quantum.domain.interfaces.Item;

public class QuantifiedEntry {
        private final Item item;
        private int quantity;

        public QuantifiedEntry(Item item, int quantity){
            this.item = item;
            this.quantity = quantity;
        }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }
}
