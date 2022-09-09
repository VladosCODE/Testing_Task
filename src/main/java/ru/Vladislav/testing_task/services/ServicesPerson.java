package ru.Vladislav.testing_task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Vladislav.testing_task.models.Person;
import ru.Vladislav.testing_task.repository.PersonRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServicesPerson {
    private PersonRepository personRepository;

    @Autowired
    public ServicesPerson(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    @Transactional
    public void update(int id,Person person){
        person.setId(id);
        personRepository.save(person);
    }


    public List<Person> findAll(){
        return personRepository.findAll();
    }
}
