package csd230.lab1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("PUBLICATION")
public abstract class PublicationEntity extends ProductEntity {

    public PublicationEntity() {}

    public PublicationEntity(String title, Double price) {
        super(title, price);
    }
}