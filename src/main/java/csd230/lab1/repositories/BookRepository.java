package csd230.lab1.repositories;

import csd230.lab1.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthor(String author);

    List<BookEntity> findByAuthorLike(String pattern);

    @Query("SELECT b FROM BookEntity b WHERE b.author LIKE %?1%")
    List<BookEntity> searchAuthorContains(String keyword);
}