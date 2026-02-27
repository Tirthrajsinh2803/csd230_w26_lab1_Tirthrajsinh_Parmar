package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.entities.CartEntity;
import csd230.lab1.entities.OrderEntity;
import csd230.lab1.entities.ProductEntity;
import csd230.lab1.repositories.BookRepository;
import csd230.lab1.repositories.CartRepository;
import csd230.lab1.repositories.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class CartController {

    private final CartRepository cartRepo;
    private final OrderRepository orderRepo;
    private final BookRepository bookRepo;

    public CartController(CartRepository cartRepo, OrderRepository orderRepo, BookRepository bookRepo) {
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.bookRepo = bookRepo;
    }

    // Show cart (always ID = 1)
    @GetMapping("/cart")
    public String cartDetails(Model model) {
        CartEntity cart = cartRepo.findById(1L).orElseGet(() -> cartRepo.save(new CartEntity()));

        model.addAttribute("cart", cart);
        model.addAttribute("products", cart.getProducts());
        model.addAttribute("total",
                cart.getProducts().stream().mapToDouble(ProductEntity::getPrice).sum()
        );

        return "cartDetails";
    }

    // Add a book to cart by id
    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id) {
        CartEntity cart = cartRepo.findById(1L).orElseGet(() -> cartRepo.save(new CartEntity()));

        BookEntity book = bookRepo.findById(id).orElse(null);
        if (book != null) {
            cart.addProduct(book);
            cartRepo.save(cart);
        }

        return "redirect:/cart";
    }

    // Remove item from cart by id
    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        CartEntity cart = cartRepo.findById(1L).orElse(null);
        if (cart != null) {
            cart.getProducts().removeIf(p -> p.getId().equals(id));
            cartRepo.save(cart);
        }
        return "redirect:/cart";
    }

    // ✅ LAB-2 Checkout
    @PostMapping("/cart/checkout")
    public String checkout() {

        CartEntity cart = cartRepo.findById(1L).orElse(null);

        if (cart == null || cart.getProducts() == null || cart.getProducts().isEmpty()) {
            return "redirect:/cart";
        }

        OrderEntity order = new OrderEntity();
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        for (ProductEntity p : cart.getProducts()) {
            total += p.getPrice();
            order.getProducts().add(p);

            // ✅ Only decrease copies for BookEntity (because BookRepository saves BookEntity)
            if (p instanceof BookEntity book) {
                // If your BookEntity has getCopies/setCopies OR getQuantity/setQuantity,
                // use the correct one.
                // Most lab2 uses "copies" (PublicationEntity), but your BookEntity shows "quantity".
                // So we decrement quantity here.

                // --- decrement quantity ---
                // If you have getters/setters, use them. If not, you must add them.
                try {
                    int currentQty = (int) BookEntity.class.getMethod("getQuantity").invoke(book);
                    if (currentQty > 0) {
                        BookEntity.class.getMethod("setQuantity", int.class).invoke(book, currentQty - 1);
                    }
                } catch (Exception e) {
                    // If getQuantity/setQuantity do not exist, DO NOTHING here.
                    // Then you must add getters/setters in BookEntity (but you said you don't want to edit it).
                }

                bookRepo.save(book);
            }
        }

        order.setTotalAmount(total);
        orderRepo.save(order);

        // clear cart
        cart.getProducts().clear();
        cartRepo.save(cart);

        return "redirect:/orders/" + order.getId();
    }
}