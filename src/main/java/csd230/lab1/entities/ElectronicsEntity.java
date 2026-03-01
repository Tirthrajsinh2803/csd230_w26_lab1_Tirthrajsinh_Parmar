package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ELECTRONICS")
public class ElectronicsEntity extends ProductEntity {

    @Column(nullable = false)
    private Integer quantity = 0;

    public ElectronicsEntity() {}

    public ElectronicsEntity(String title, Integer quantity, Double price) {
        super(title, price);
        this.quantity = (quantity == null ? 0 : quantity);
    }

    @Override
    public boolean sellItem() {
        if (quantity != null && quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = (quantity == null ? 0 : quantity); }
}