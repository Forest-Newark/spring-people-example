package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by forestnewark on 4/13/17.
 */
@Controller
public class PeopleController {

    @Autowired
    PeopleRepository repo;

    @GetMapping("/")
    public String listPeople(Model model, @RequestParam(defaultValue = "") String search){
        model.addAttribute("search", search);
      model.addAttribute("people",repo.listPeople(search));
        return "index";
    }
    @GetMapping("/orderFirstName")
    public String orderFirstName(Model model, @RequestParam(defaultValue = "") String search){
        model.addAttribute("search", search);
        model.addAttribute("people",repo.listPeopleFirstName(search));
        return "index";
    }
    @GetMapping("/orderLastName")
    public String orderLastName(Model model, @RequestParam(defaultValue = "") String search){
        model.addAttribute("search", search);
        model.addAttribute("people",repo.listPeopleLastName(search));
        return "index";
    }

    @GetMapping("/personForm")
    public String personForm(Model model,Integer personid)
    {
        if(personid == null){
            model.addAttribute("person",new Person());
        }else{
            model.addAttribute("person",repo.getPerson(personid));
        }
        return "personForm";
    }

    @RequestMapping(value= "/savePerson", method = RequestMethod.POST)
    public String savePerson(Person person){
        System.out.println(person);
        repo.updatePerson(person);
        return "redirect:/";
    }

}
