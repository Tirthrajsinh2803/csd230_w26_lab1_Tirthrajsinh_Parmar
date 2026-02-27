package csd230.lab1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Also ignore products in JSON to prevent loops in /carts endpoints
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void addProduct(ProductEntity product) {
        this.products.add(product);
        product.addCartInternal(this);
    }

    public void removeProduct(ProductEntity product) {
        this.products.remove(product);
        product.removeCartInternal(this);
    }

    @Override
    public String toString() {
        return "CartEntity{id=" + id + ", productsCount=" + products.size() + "}";
    }
}