package csd230.lab1.controllers;

import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.entities.UserEntity;
import csd230.lab1.repositories.CartRepository;
import csd230.lab1.repositories.OrderRepository;
import csd230.lab1.repositories.ProductRepository;
import csd230.lab1.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartController(CartRepository cartRepository,
                          ProductRepository productRepository,
                          UserRepository userRepository,
                          OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // View Cart
    @GetMapping
    public String viewCart(Model model, Authentication authentication) {

        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartEntity cart = cartRepository.findByUser(user).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        model.addAttribute("cartItems", cart.getProducts());
        model.addAttribute("total", cart.getTotal());

        return "cartDetails";
    }

    // Add to cart
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            Authentication authentication) {

        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartEntity cart = cartRepository.findByUser(user).orElseGet(() -> {
            CartEntity newCart = new CartEntity();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.getProducts().add(product);
        cartRepository.save(cart);

        return "redirect:/products";
    }

    // Remove from cart
    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId,
                                 Authentication authentication) {

        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getProducts().removeIf(p -> p.getId().equals(productId));
        cartRepository.save(cart);

        return "redirect:/cart";
    }

    // Checkout
    @PostMapping("/checkout")
    public String checkout(Authentication authentication) {

        UserEntity user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());
        order.setProducts(cart.getProducts());
        order.setTotalAmount(cart.getTotal());

        orderRepository.save(order);

        cart.getProducts().clear();
        cartRepository.save(cart);

        return "redirect:/orders/" + order.getId();
    }
}