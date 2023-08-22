package com.example.demo.personCSV;

import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceCSV {

    private final PersonRepositoryCSV personRepositoryCSV;

    @Autowired
    public PersonServiceCSV(PersonRepositoryCSV personRepositoryCSV) {
        this.personRepositoryCSV = personRepositoryCSV;
    }

    public Optional<Person> getPerson(Long personid) {
        return personRepositoryCSV.findById(personid);
    }

    public List<Person> getPersons() {
        return personRepositoryCSV.findAll();
    }

    public void addNewPerson(Person person) {
        personRepositoryCSV.save(person);
    }

    public void updatePerson(Long personId, Person person) {
        personRepositoryCSV.updatePerson(personId, person);
    }

    public void deletePerson(Long personId) {
        personRepositoryCSV.deleteById(personId);
    }
}
