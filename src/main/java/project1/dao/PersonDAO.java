package project1.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project1.models.Book;
import project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    public Person index(int id){
        Session session = sessionFactory.getCurrentSession();

        return session.get(Person.class, id);
    }

    @Transactional
    public List<Person> show(){
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT p from Person  p", Person.class)
            .getResultList();
    }

    @Transactional
    public List<Book> getBooksOfPerson(int owner_id){
//        return jdbcTemplate.query("SELECT *FROM Book WHERE owner_id=?",
//            new BeanPropertyRowMapper<>(Book.class),owner_id);
        return null;
    }
    @Transactional
    public void save(Person person){
//        jdbcTemplate.update("INSERT INTO person(fullname, year, email) VALUES (?,?,?)",
//                person.getFullName(),person.getYear(),person.getEmail());
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);

    }

    @Transactional
    public void update(int id, Person updatedPerson){
        Session session = sessionFactory.getCurrentSession();

        Person personToBeUpdated = session.get(Person.class, id);
        personToBeUpdated.setEmail(updatedPerson.getEmail());
        personToBeUpdated.setYear(updatedPerson.getYear());
        personToBeUpdated.setFullName(updatedPerson.getFullName());
    }

    @Transactional
    public void delete(int id){
        Session session= sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    //для валидации уник ФИО
//    public Optional<Person> getPersonByFullName(String fullName){
//        return jdbcTemplate.query("SELECT *FROM Person WHERE fullName=?",new Object[]{fullName},
//            new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
//    }
}
