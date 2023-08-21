package com.example.demo.personXML;

import com.example.demo.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class PersonControllerXML {

    private final PersonServiceXML personServiceXML;

    @Autowired
    public PersonControllerXML(PersonServiceXML personServiceXML) {
        this.personServiceXML = personServiceXML;
    }

    @GetMapping("/personxml/{personId}")
    public Optional<Person> getPerson(@PathVariable Long personId) {
        return personServiceXML.getPerson(personId);
    }

    @GetMapping("/personsxml")
    public List<Person> getPersons() {
        return personServiceXML.getPersons();
    }

    @PostMapping("/personxml")
    public void addNewPerson(@RequestBody Person person) {
        personServiceXML.addNewPerson(person);
    }

    @DeleteMapping("/personxml/{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personServiceXML.deletePerson(personId);
    }

    @PutMapping("/personxml/{personId}")
    public void updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        personServiceXML.updatePerson(personId, person);
    }
}
