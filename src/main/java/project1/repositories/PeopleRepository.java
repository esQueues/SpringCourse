package project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project1.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT b.owner FROM Book b WHERE b.id = :bookId")
    Optional<Person> findOwnerByBookId(@Param("bookId") int bookId);

    List<Person> findByFullName(String name);

    List<Person> findByFullNameOrderByYear(String name);

    List<Person> findByEmail(String email);

    List<Person> findByFullNameStartingWith(String startingWith);

    List<Person> findByFullNameOrEmail(String name,String email);
}
