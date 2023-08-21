package com.example.demo.personXML;

import com.example.demo.person.Person;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceXML {

    private final PersonRepositoryXML personRepositoryXML;

    @Autowired
    public PersonServiceXML(PersonRepositoryXML personRepositoryXML) {
        this.personRepositoryXML = personRepositoryXML;
    }

    public Optional<Person> getPerson(Long personId) {
        return personRepositoryXML.findById(personId);
    }

    public List<Person> getPersons() {
        return personRepositoryXML.findAll();
    }

    public void addNewPerson(Person person) {
        personRepositoryXML.save(person);
    }

    public void deletePerson(Long personId) {
        Optional<Person> person = Optional.of(personRepositoryXML.findById(personId))
                        .orElseThrow(() -> new IllegalArgumentException(
                           "Person with id " + personId + " does not exists"
                        ));
        if(person.isPresent()) {
            personRepositoryXML.deleteById(personId);
        }
    }

    @Transactional
    public void updatePerson(Long personId, Person person) {
        Person personDB = personRepositoryXML.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Person with id " + personId + " does not exists"
                ));
        personRepositoryXML.updatePerson(personId, person);
    }
}
