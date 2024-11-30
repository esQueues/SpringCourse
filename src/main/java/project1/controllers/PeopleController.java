package project1.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project1.dao.PersonDAO;
import project1.models.Person;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String show( Model model){
        model.addAttribute("people",personDAO.show());
        return "people/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id")int id,Model model){
        model.addAttribute(personDAO.index(id));
        model.addAttribute("books",personDAO.getBooksOfPerson(id));
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person")Person person){
        return "people/new";
    }

    @PostMapping() //@RequestParam("name")String name,@RequestParam("age")
    public String save(@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "people/new";


        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
         model.addAttribute("person",personDAO.index(id));
         return "people/edit";
    }
    @PostMapping("/{id}")
    public String update(@PathVariable("id")int id,@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id,person);
        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
