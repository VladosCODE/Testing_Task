package ru.Vladislav.testing_task.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.Vladislav.testing_task.models.Person;
import ru.Vladislav.testing_task.models.PersonDTO;
import ru.Vladislav.testing_task.services.ServicesPerson;
import ru.Vladislav.testing_task.util.PersonNotCreatedException;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/phoneBook")
@RestController
public class ControllerPerson {
    private ServicesPerson servicesPerson;
    private ModelMapper modelMapper;

    @Autowired
    public ControllerPerson(ServicesPerson servicesPerson, ModelMapper modelMapper) {
        this.servicesPerson = servicesPerson;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ModelAndView showPersons(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("persons",servicesPerson.findAll());
        modelAndView.setViewName("main_page");
        return modelAndView;
    }
    @PostMapping(path="/update",consumes = "application/json")
    public Person updatePerson(@RequestBody @Valid PersonDTO personDTO,BindingResult bindingResult,
                                                @RequestParam("id") int id){
        Person person = convertToPerson(personDTO);
        check_exception(bindingResult);
        servicesPerson.update(id,person);
        return person;
    }
    @PostMapping(path = "/delete")
    public int deletePerson(@RequestParam Integer id){
        servicesPerson.delete(id);
        return id;
    }
    @PostMapping(path="/delete/cancel")
    public Person cancel_delete(@RequestParam("id") int id){
        Person person = servicesPerson.cancel_delete(id);
        return person;
    }

    @PostMapping(path = "/create",consumes = "application/json")
    public Person createPerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        Person person = convertToPerson(personDTO);
        check_exception(bindingResult);
        servicesPerson.save(person);
        return person;
    }

    @PostMapping(path="/updateAllRecords")
    public String update_all_name(@RequestParam(value = "name") String name){
        servicesPerson.update_date(name);
        return name;
    }

    @ExceptionHandler
    private ResponseEntity handleException(PersonNotCreatedException e){
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
    private Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO,Person.class);
    }
}
