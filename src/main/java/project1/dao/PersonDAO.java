package project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project1.models.Person;

import java.sql.ResultSet;
import java.util.List;
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Person index(int id){
        return jdbcTemplate.query("SELECT *FROM Person WHERE id=?",
                new BeanPropertyRowMapper<>(Person.class),id).stream().findAny().orElse(null);
    }
    public List<Person> show(){
        return jdbcTemplate.query("SELECT *FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person(fullname, year, email) VALUES (?,?,?)",
                person.getFullName(),person.getYear(),person.getEmail());
    }
    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE person set fullname=?, year=?, email=? WHERE id=?",
                updatedPerson.getFullName(),updatedPerson.getYear(), updatedPerson.getEmail(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person where id=?",id);
    }
}
