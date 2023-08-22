package com.example.demo.person;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryInterface {

    List<Person> findAll();

    Optional<Person> findById(Long personId);

    void save(Person person);

    void updatePerson(Long personId, Person person);

    void deleteById(Long personId);
}
