package project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project1.models.Book;

import java.util.List;

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

}
