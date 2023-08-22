package com.example.demo.personCSV;

import com.example.demo.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PersonControllerCSV {

    private final PersonServiceCSV personServiceCSV;

    @Autowired
    public PersonControllerCSV(PersonServiceCSV personServiceCSV) {
        this.personServiceCSV = personServiceCSV;
    }

    @GetMapping("/personcsv/{personId}")
    public Optional<Person> getPerson(@PathVariable Long personId) {
        return personServiceCSV.getPerson(personId);
    }

    @GetMapping("/personscsv")
    public List<Person> getPersons() {
        return personServiceCSV.getPersons();
    }

    @PostMapping("/personcsv")
    public void addNewPerson(@RequestBody Person person) {
        personServiceCSV.addNewPerson(person);
    }

    @PutMapping("/personcsv/{personId}")
    public void updatePerson(@PathVariable Long personId, @RequestBody Person updatedPerson) {
        personServiceCSV.updatePerson(personId, updatedPerson);
    }

    @DeleteMapping("/personcsv/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personServiceCSV.deletePerson(personId);
    }
}
