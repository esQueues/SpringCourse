package project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project1.models.Book;
import project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book index(int id){
        return jdbcTemplate.query("SELECT *FROM Book WHERE id=?",
                new BeanPropertyRowMapper<>(Book.class),id).stream().findAny().orElse(null);
    }

    public List<Book> show(){
        return jdbcTemplate.query("SELECT *FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public void update(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE book set title=?, year=?, author=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getYear(), updatedBook.getAuthor(),id);
    }



    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book where id=?",id);
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(title,author,year) VALUES(?,?,?)",
                book.getTitle(),book.getAuthor(),book.getYear());
    }
/////////////////////////////////////////////////////////////////
    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Person.* FROM book  JOIN person on  book.owner_id = person.id "+
            "WHERE book.id=?", new Object[]{id},
            new BeanPropertyRowMapper<>(Person.class))
            .stream().findAny();
    }
    //////////////////////////////////////////
    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET owner_id=null WHERE id=?",id);
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET owner_id=? WHERE id=?",selectedPerson.getId(),id);
    }
}