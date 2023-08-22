package com.example.demo.personJSON;

import com.example.demo.person.Person;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class PersonControllerJSON {

    private final PersonServiceJSON personService;

    public PersonControllerJSON(PersonServiceJSON personService) {
        this.personService = personService;
    }

    @GetMapping("/personsjson")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/personjson/{personId}")
    public Optional<Person> getPerson(@PathVariable Long personId) {
        return personService.getPerson(personId);
    }

    @PostMapping("/personjson")
    public void createPerson(@RequestBody Person person) {
        personService.createPerson(person);
    }

    @PutMapping("/personjson/{personId}")
    public void updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        personService.updatePerson(personId, person);
    }

    @DeleteMapping("/personjson/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personService.deletePerson(personId);
    }

}
