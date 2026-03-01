package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DISCMAG")
public class DiscMagEntity extends MagazineEntity {

    public DiscMagEntity() {}

    public DiscMagEntity(String title, Integer quantity, Double price) {
        super(title, quantity, price);
    }

    @Override
    public boolean sellItem() {
        // you can add custom behavior if you want
        return super.sellItem();
    }
}