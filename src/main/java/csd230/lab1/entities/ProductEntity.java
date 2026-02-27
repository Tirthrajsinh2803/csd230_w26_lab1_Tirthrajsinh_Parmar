package csd230.lab1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
public abstract class ProductEntity implements Serializable, SaleableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Prevent infinite JSON loop (Product -> Carts -> Products -> Carts ...)
    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private Set<CartEntity> carts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<CartEntity> getCarts() {
        return carts;
    }

    // Keep relationship consistent (used by CartEntity helper)
    void addCartInternal(CartEntity cart) {
        this.carts.add(cart);
    }

    void removeCartInternal(CartEntity cart) {
        this.carts.remove(cart);
    }

    // subclasses must implement these
    public abstract double getPrice();
}