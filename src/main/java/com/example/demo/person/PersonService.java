package com.example.demo.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> getPerson(Long personId) {
        return Optional.of(personRepository.findById(personId))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Person with"  + personId +  " does not exists"
                ));
    }

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public void addNewPerson(Person person) {
        personRepository.save(person);
    }

    public void deletePerson(Long personId) {
        boolean exists = personRepository.existsById(personId);

        if(!exists) {
            throw new IllegalStateException("Person with"  + personId + " does not exists");
        }
        personRepository.deleteById(personId);
    }

    public void updatePerson(Long personId, Person person) {
        Person personDB = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Person with"  + personId + " does not exists"
                ));

        if(person.equals(personDB)){
            return;
        }

        if(person.getName() != null && !person.getName().isBlank()) {
            personDB.setName(person.getName());
        }

        if(person.getDob() != null) {
            personDB.setDob(person.getDob());
        }

        if(person.getEmail() != null && !person.getEmail().isBlank()) {
            personDB.setEmail(person.getEmail());
        }

        if(person.getTelephone() != null && !person.getTelephone().isBlank()) {
            personDB.setTelephone(person.getTelephone());
        }
    }
}
