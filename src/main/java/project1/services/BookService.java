package project1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.models.Book;
import project1.models.Person;
import project1.repositories.BookRepository;
import project1.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }



    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    public Book findOneBook(int id){
        Optional<Book> book= bookRepository.findById(id);
        return book.orElse(null);
    }

    public void saveBook(Book book){
        bookRepository.save(book);
    }

    public void updateBook(int id, Book updated_book) {
        updated_book.setId(id);
        bookRepository.save(updated_book);
    }

    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }

    public Optional<Person> findOwner(int id){

        return peopleRepository.findOwnerByBookId(id);
    }

    public void release(int id){
        bookRepository.releaseBook(id);
    }

    public void assign(int id, Person owner){
        bookRepository.assignOwner(id,owner);
    }

}
