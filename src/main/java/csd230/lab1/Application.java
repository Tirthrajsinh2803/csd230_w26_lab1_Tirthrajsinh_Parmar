package csd230.lab1;

import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private final BookRepository bookRepo;

	public Application(BookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	@Profile("mysql")
	public void run(String... args) {

		// ✅ Seed Books only if none exist
		if (bookRepo.count() == 0) {
			BookEntity b1 = new BookEntity("Java Basics", "Tirth", 10, 29.99);
			BookEntity b2 = new BookEntity("Spring Boot", "Carella", 5, 39.99);
			BookEntity b3 = new BookEntity("Java Advanced", "Tirth", 8, 49.99);

			bookRepo.save(b1);
			bookRepo.save(b2);
			bookRepo.save(b3);
		}
	}
}