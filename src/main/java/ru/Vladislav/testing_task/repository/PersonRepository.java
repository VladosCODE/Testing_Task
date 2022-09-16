package ru.Vladislav.testing_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.Vladislav.testing_task.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    @Modifying
    @Query(value = "UPDATE Person p set p.deleted=false WHERE p.id = :id")
    void cancel_delete(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE Person p set p.name= :name WHERE p.deleted=false")
    void update_date(@Param("name") String name);
}
