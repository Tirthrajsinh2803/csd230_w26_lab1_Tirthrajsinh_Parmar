package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("BOOK")
public class BookEntity extends ProductEntity {

    private String title;
    private double price;
    private int quantity;
    private String author;

    public BookEntity() {}

    public BookEntity(String title, double price, int quantity, String author) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.author = author;
    }

    public String getAuthor() { return author; }

    @Override
    public void sellItem() {
        System.out.println("Sold Book: " + title);
    }

    @Override
    public double getPrice() {
        return price;
    }
}