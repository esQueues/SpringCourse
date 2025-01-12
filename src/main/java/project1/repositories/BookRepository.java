package project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project1.models.Book;
import project1.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitle(String title);
    List<Book> findByOwnerId(int personId);
    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.owner = null WHERE b.id = :id")
    void releaseBook(@Param("id") int id);


    @Modifying
    @Transactional
    @Query("update Book b set b.owner= :person where b.id=:id")
    void assignOwner(@Param("id") int id, @Param("person") Person person);








}
