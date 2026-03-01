package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
public abstract class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false)
    protected Double price;

    public ProductEntity() {}

    public ProductEntity(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    // ✅ IMPORTANT: all subclasses must match these EXACT signatures
    public abstract boolean sellItem();
    public abstract Double getPrice();

    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public void setPrice(Double price) { this.price = price; }
}