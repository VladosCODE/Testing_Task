package ru.Vladislav.testing_task.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.Vladislav.testing_task.models.Person;
import ru.Vladislav.testing_task.services.ServicesPerson;
import ru.Vladislav.testing_task.util.PersonNotCreatedException;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/phoneBook")
@Controller
public class ControllerPerson {
    private ServicesPerson servicesPerson;

    @Autowired
    public ControllerPerson(ServicesPerson servicesPerson) {
        this.servicesPerson = servicesPerson;
    }

    @GetMapping("")
    public String showPersons(Model model){
        model.addAttribute("persons",servicesPerson.findAll());
        return "main_page";
    }
    @PostMapping(path="/update",consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Integer> updatePerson(@RequestBody @Valid Person person,BindingResult bindingResult,@RequestParam("id") int id){
        check_exception(bindingResult);
        servicesPerson.update(id,person);
        return new ResponseEntity<>(person.getId(), HttpStatus.OK);
    }
    @PostMapping(path = "/delete")
    @ResponseBody
    public int deletePerson(@RequestParam Integer id){
        servicesPerson.delete(id);
        return id;
    }

    @PostMapping(path = "/create",consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Integer> createPerson(@RequestBody @Valid Person person, BindingResult bindingResult){
        check_exception(bindingResult);
        servicesPerson.save(person);
        return new ResponseEntity<>(person.getId(), HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity handleException(PersonNotCreatedException e){
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    private void check_exception(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).
                        append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
    }
}
