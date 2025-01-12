package project1.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project1.models.Book;
import project1.models.Person;
import project1.services.BookService;
import project1.services.PeopleService;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

//    private final BookDAO bookDAO;
//    private final PersonDAO personDAO;
//
//    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
//        this.bookDAO = bookDAO;
//        this.personDAO = personDAO;
//    }
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }


    @GetMapping
    public String show(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "books/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOneBook(id));
        Optional<Person> bookOwner= bookService.findOwner(id);

        if(bookOwner.isPresent())
            model.addAttribute("owner",bookOwner.get());

        else
            model.addAttribute("people",peopleService.findAll());

        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOneBook(id));
        return "books/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookService.release(id);
        return "redirect:/books/"+id;
    }

    @PostMapping("{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookService.assign(id, selectedPerson);
        return "redirect:/books/"+id;
    }

}