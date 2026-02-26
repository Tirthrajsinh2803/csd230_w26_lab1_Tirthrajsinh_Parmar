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

	/**
	 * ✅ Lecture 2.5 prereq: App must run and MVC controllers must work.
	 * This runner only seeds sample data IF tables are empty.
	 * It will not spam console or break controllers.
	 */
	@Override
	public void run(String... args) {

		// Seed Books only if none exist
		if (bookRepo.count() == 0) {
			BookEntity b1 = new BookEntity("Java Basics", 29.99, 10, "Tirth");
			BookEntity b2 = new BookEntity("Spring Boot", 39.99, 5, "Carella");
			BookEntity b3 = new BookEntity("Java Advanced", 49.99, 8, "Tirth");
			bookRepo.save(b1);
			bookRepo.save(b2);
			bookRepo.save(b3);
		}

		// Seed Magazine only if none exist
		if (magazineRepo.count() == 0) {
			MagazineEntity m1 = new MagazineEntity(
					"Tech Monthly", 9.99, 20,
					101, LocalDateTime.now()
			);
			magazineRepo.save(m1);
		}

		// Seed DiscMag only if none exist
		if (discMagRepo.count() == 0) {
			DiscMagEntity dm1 = new DiscMagEntity(
					"Gaming Special", 12.99, 15,
					202, LocalDateTime.now(), true
			);
			discMagRepo.save(dm1);
		}

		// Seed Ticket only if none exist
		if (ticketRepo.count() == 0) {
			TicketEntity t1 = new TicketEntity("Concert", 55.00);
			ticketRepo.save(t1);
		}

		// Seed Electronics only if none exist
		if (electronicsRepo.count() == 0) {
			ElectronicsEntity e1 = new ElectronicsEntity("Headphones", "Sony", 99.99);
			electronicsRepo.save(e1);
		}

		// Create one cart if none exists (used later in cart exercise)
		if (cartRepo.count() == 0) {
			CartEntity cart = new CartEntity();
			cartRepo.save(cart);
		}
	}
}