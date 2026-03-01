package csd230.lab1.controllers;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepo;

    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    // ✅ LIST
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        return "bookList";
    }

    // ✅ DETAILS
    @GetMapping("/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        BookEntity book = bookRepo.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "bookDetails";
    }

    // ✅ SHOW ADD
    @GetMapping("/add")
    public String showAddForm() {
        return "addBook";
    }

    // ✅ ADD
    @PostMapping("/add")
    public String addBook(
            @RequestParam("title") String title,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("author") String author
    ) {
        BookEntity book = new BookEntity(title, author, quantity, price);
        bookRepo.save(book);
        return "redirect:/books";
    }

    // ✅ SHOW EDIT
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookEntity book = bookRepo.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "editBook";
    }

    // ✅ EDIT (safe delete+insert because entity has missing setters/getters)
    @PostMapping("/edit")
    public String updateBook(
            @RequestParam("id") Long id,
            @RequestParam("title") String title,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("author") String author
    ) {
        if (bookRepo.existsById(id)) {
            bookRepo.deleteById(id);
        }
        BookEntity updated = new BookEntity(title, author, quantity, price);
        bookRepo.save(updated);
        return "redirect:/books";
    }

    // ✅ DELETE
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepo.deleteById(id);
        return "redirect:/books";
    }
}