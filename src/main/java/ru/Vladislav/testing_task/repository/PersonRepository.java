package ru.Vladislav.testing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Vladislav.testing_task.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
}
