package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ELECTRONICS")
public class ElectronicsEntity extends ProductEntity {

    private String name;
    private String brand;
    private double price;

    // Default constructor (required by JPA)
    public ElectronicsEntity() {}

    public ElectronicsEntity(String name, String brand, double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public void sellItem() {
        System.out.println("Sold Electronics: " + brand + " " + name + " for $" + price);
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ElectronicsEntity{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}