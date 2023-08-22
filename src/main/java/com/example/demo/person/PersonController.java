package com.example.demo.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/{personId}")
    public Optional<Person> getPerson(@PathVariable Long personId) {
        return personService.getPerson(personId);
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @PostMapping("/person")
    public void addNewPerson(@RequestBody Person person) {
        personService.addNewPerson(person);
    }

    @PutMapping("/person/{personId}")
    public void updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        personService.updatePerson(personId, person);
    }

    @DeleteMapping("/person/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }
}
