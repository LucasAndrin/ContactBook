package com.example.demo.personJSON;

import com.example.demo.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceJSON {

    private final PersonRepositoryJSON personRepositoryJSON;

    @Autowired
    public PersonServiceJSON(PersonRepositoryJSON personRepositoryJSON) {
        this.personRepositoryJSON = personRepositoryJSON;
    }

    public List<Person> getPersons() {
        return personRepositoryJSON.findAll();
    }

    public Optional<Person> getPerson(Long personId) {
        return personRepositoryJSON.getPerson(personId);
    }


    public void createPerson(Person person) {
        personRepositoryJSON.createPerson(person);
    }
    public void updatePerson(Long personId, Person person) {
        personRepositoryJSON.updatePerson(personId, person);
    }

    public void deletePerson(Long personId) {
        personRepositoryJSON.deletePerson(personId);
    }
}
