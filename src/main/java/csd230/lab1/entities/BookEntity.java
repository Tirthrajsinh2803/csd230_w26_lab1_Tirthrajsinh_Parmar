package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOOK")
public class BookEntity extends PublicationEntity {

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer copies = 0;  // ✅ prevents null

    public BookEntity() {}

    // ✅ This is the constructor IntelliJ shows your app expects:
    // BookEntity(String, String, Integer, Double)
    public BookEntity(String title, String author, Integer copies, Double price) {
        super(title, price);
        this.author = author;
        this.copies = (copies == null ? 0 : copies);
    }

    @Override
    public boolean sellItem() {
        if (copies != null && copies > 0) {
            copies--;
            return true;
        }
        return false;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getCopies() { return copies; }
    public void setCopies(Integer copies) { this.copies = (copies == null ? 0 : copies); }
}