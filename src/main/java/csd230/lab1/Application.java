package csd230.lab1;

import csd230.lab1.entities.*;
import csd230.lab1.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final BookRepository bookRepo;
	private final MagazineRepository magazineRepo;
	private final DiscMagRepository discMagRepo;
	private final TicketRepository ticketRepo;
	private final CartRepository cartRepo;
	private final ElectronicsRepository electronicsRepo;

	public Application(BookRepository bookRepo,
					   MagazineRepository magazineRepo,
					   DiscMagRepository discMagRepo,
					   TicketRepository ticketRepo,
					   CartRepository cartRepo,
					   ElectronicsRepository electronicsRepo) {
		this.bookRepo = bookRepo;
		this.magazineRepo = magazineRepo;
		this.discMagRepo = discMagRepo;
		this.ticketRepo = ticketRepo;
		this.cartRepo = cartRepo;
		this.electronicsRepo = electronicsRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println("===== CREATE =====");

		BookEntity b1 = new BookEntity("Java Basics", 29.99, 10, "Tirth");
		BookEntity b2 = new BookEntity("Spring Boot", 39.99, 5, "Carella");

		bookRepo.save(b1);
		bookRepo.save(b2);

		MagazineEntity m1 = new MagazineEntity(
				"Tech Monthly", 9.99, 20,
				101, LocalDateTime.now()
		);
		magazineRepo.save(m1);

		DiscMagEntity dm1 = new DiscMagEntity(
				"Gaming Special", 12.99, 15,
				202, LocalDateTime.now(), true
		);
		discMagRepo.save(dm1);

		TicketEntity t1 = new TicketEntity("Concert", 55.00);
		ticketRepo.save(t1);

		ElectronicsEntity e1 =
				new ElectronicsEntity("Headphones", "Sony", 99.99);
		electronicsRepo.save(e1);

		System.out.println("===== READ =====");

		bookRepo.findAll().forEach(System.out::println);

		System.out.println("Derived Query:");
		bookRepo.findByAuthor("Tirth").forEach(System.out::println);

		System.out.println("LIKE Query:");
		bookRepo.findByAuthorLike("%ir%").forEach(System.out::println);

		System.out.println("@Query:");
		bookRepo.searchAuthorContains("are").forEach(System.out::println);

		System.out.println("===== UPDATE =====");

		b1 = bookRepo.findById(b1.getId()).orElseThrow();
		b1 = new BookEntity("Java Advanced", 49.99, 8, "Tirth");
		bookRepo.save(b1);

		System.out.println("===== DELETE =====");

		ticketRepo.delete(t1);
		System.out.println("Tickets count: " + ticketRepo.count());

		System.out.println("===== MANY-TO-MANY =====");

		CartEntity cart = new CartEntity();
		cartRepo.save(cart);

		cart.addProduct(b2);
		cart.addProduct(e1);
		cartRepo.save(cart);

		System.out.println("Cart contains:");
		cart.getProducts().forEach(p -> {
			System.out.println(p);
			p.sellItem();
		});

		System.out.println("===== DONE =====");
	}
}