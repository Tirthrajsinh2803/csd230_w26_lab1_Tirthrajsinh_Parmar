package csd230.lab1.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cart belongs to ONE user
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserEntity user;

    // Cart contains many products
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new HashSet<>();

    public CartEntity() {}

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    // used by controller if you want helper methods
    public void addProduct(ProductEntity product) {
        this.products.add(product);
    }

    public void removeProduct(ProductEntity product) {
        this.products.remove(product);
    }

    // ✅ CartController uses cart.getTotal()
    @Transient
    public double getTotal() {
        return products.stream()
                .mapToDouble(ProductEntity::getPrice)
                .sum();
    }
}