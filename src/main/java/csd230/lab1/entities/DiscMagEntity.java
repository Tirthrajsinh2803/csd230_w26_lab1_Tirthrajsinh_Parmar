package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("DISCMAG")
public class DiscMagEntity extends MagazineEntity {

    private boolean hasDisc;

    public DiscMagEntity() {}

    public DiscMagEntity(String title, double price, int quantity,
                         int issueNumber, LocalDateTime publicationDate,
                         boolean hasDisc) {
        super(title, price, quantity, issueNumber, publicationDate);
        this.hasDisc = hasDisc;
    }

    @Override
    public void sellItem() {
        System.out.println("Sold Disc Magazine");
    }
}