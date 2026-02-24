package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("MAG")
public class MagazineEntity extends ProductEntity {

    private String title;
    private double price;
    private int quantity;
    private int issueNumber;
    private LocalDateTime publicationDate;

    public MagazineEntity() {}

    public MagazineEntity(String title, double price, int quantity,
                          int issueNumber, LocalDateTime publicationDate) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.issueNumber = issueNumber;
        this.publicationDate = publicationDate;
    }

    @Override
    public void sellItem() {
        System.out.println("Sold Magazine: " + title);
    }

    @Override
    public double getPrice() {
        return price;
    }
}