package project1.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project1.dao.PersonDAO;
import project1.models.Person;
import project1.services.PeopleService;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;


    @Autowired
    public PeopleController( PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String show( Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id")int id,Model model){
        model.addAttribute(peopleService.findOne(id));
        model.addAttribute("books",peopleService.getBooksOfPerson(id));
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


        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
         model.addAttribute("person",peopleService.findOne(id));
         return "people/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id")int id,@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id,person);
        return "redirect:/people";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}
