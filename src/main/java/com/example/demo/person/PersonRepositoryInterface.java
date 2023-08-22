package com.example.demo.person;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryInterface {

    List<Person> findAll();

    Optional<Person> findById(Long personId);

    void deleteById(Long personId);

    void updatePerson(Long personId, Person person);

    void save(Person person);
}
