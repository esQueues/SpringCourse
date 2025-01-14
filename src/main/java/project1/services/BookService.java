package project1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<Book> findAllBooks(boolean sortByYear){
        if(sortByYear)
           return bookRepository.findAll(Sort.by("year"));
        else
            return bookRepository.findAll();
    }

    public List<Book> findAllBooksPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if(sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> searchBooks(String startingWith){
        return bookRepository.findBooksByTitleContainsIgnoreCase(startingWith);
    }

    public Book findOneBook(int id){
        Optional<Book> book= bookRepository.findById(id);
        return book.orElse(null);
    }

    public void saveBook(Book book){
        bookRepository.save(book);
    }

    public void updateBook(int id, Book updated_book) {
        Book bookToBeUpdated=bookRepository.findById(id).get();
        updated_book.setId(id);
        updated_book.setOwner(bookToBeUpdated.getOwner());
        bookRepository.save(updated_book);
    }

    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }

    public Optional<Person> findOwner(int id){
        return peopleRepository.findOwnerByBookId(id);
    }

    public void release(int id){
        bookRepository.findById(id).ifPresent(
            book -> {
                book.setOwner(null);
            }
        );
    }

    public void assign(int id, Person owner){
        bookRepository.findById(id).ifPresent(
            book -> {
                book.setOwner(owner);
            }
        );
    }

}
