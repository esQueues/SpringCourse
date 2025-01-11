package project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project1.models.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    List<Person> findByFullName(String name);

    List<Person> findByFullNameOrderByYear(String name);

    List<Person> findByEmail(String email);

    List<Person> findByFullNameStartingWith(String startingWith);

    List<Person> findByFullNameOrEmail(String name,String email);
}
