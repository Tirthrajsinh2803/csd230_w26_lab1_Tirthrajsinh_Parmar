package csd230.lab1.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Home page
    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    // ✅ THIS FIXES YOUR ERROR
    @GetMapping("/products")
    public String productsRedirect() {
        return "redirect:/books";
    }
}