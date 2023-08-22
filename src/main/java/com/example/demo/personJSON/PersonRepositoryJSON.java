package com.example.demo.personJSON;

import com.example.demo.person.Person;
import com.example.demo.person.PersonRepositoryInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

@Repository
public class PersonRepositoryJSON implements PersonRepositoryInterface {

    private static final String fileName = "src/main/java/com/example/demo/personJSON/data.json";

    private JSONObject getJSON() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader(fileName);
        return (JSONObject) parser.parse(reader);
    }

    private JSONArray getPersonsJSONArray() throws IOException, ParseException {
        JSONObject obj = getJSON();
        return (JSONArray) obj.get("persons");
    }

    private JSONObject getPersonJSON(Person person) {
        JSONObject personJSON = new JSONObject();
        personJSON.put("id", person.getId());
        personJSON.put("name", person.getName());
        personJSON.put("dob", person.getDob().toString());
        personJSON.put("telephone", person.getTelephone());
        personJSON.put("email", person.getEmail());
        return personJSON;
    }

    private void setPersonsJSON(String content) throws IOException {
        FileWriter file = new FileWriter(fileName);
        file.write(content);
        file.flush();
        file.close();
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();

        try {
            for (Map<String, Object> personMap : (Iterable<Map<String, Object>>) getPersonsJSONArray()) {
                Person person = new Person();

                person.setId((Long) personMap.get("id"));
                person.setName((String) personMap.get("name"));
                person.setEmail((String) personMap.get("email"));
                person.setTelephone((String) personMap.get("telepone"));
                person.setDob(LocalDate.parse((String) personMap.get("dob")));

                persons.add(person);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return persons;
    }

    @Override
    public Optional<Person> findById(Long id) {
        try {
            for (Map<String, Object> personMap : (Iterable<Map<String, Object>>) getPersonsJSONArray()) {
                Long currentId = (Long) personMap.get("id");
                if (currentId.equals(id)) {
                    Person person = new Person();
                    person.setId(currentId);
                    person.setName((String) personMap.get("name"));
                    person.setEmail((String) personMap.get("email"));
                    person.setTelephone((String) personMap.get("telepone"));
                    person.setDob(LocalDate.parse((String) personMap.get("dob")));

                    return Optional.of(person);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void save(Person person) {
        try {
            JSONObject obj = getJSON();

            Long nextId = (Long) obj.get("nextId");
            person.setId(nextId);

            JSONArray persons = (JSONArray) obj.get("persons");
            persons.add(getPersonJSON(person));

            obj.put("persons", persons);
            obj.put("nextId", nextId + 1);

            FileWriter file = new FileWriter(fileName);
            System.out.println(obj.toJSONString());
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePerson(Long personId, Person person) {
        try {
            JSONObject obj = getJSON();

            JSONArray persons = (JSONArray) obj.get("persons");

            Iterator<Map<String, Object>> iterator = persons.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> personObj = iterator.next();
                Long id = (Long) personObj.get("id");
                if (id.equals(personId)) {
                    iterator.remove();
                    JSONObject personJSON = new JSONObject();
                    personJSON.put("id", personId);
                    personJSON.put("name", person.getName());
                    personJSON.put("dob", person.getDob().toString());
                    personJSON.put("telephone", person.getTelephone());
                    personJSON.put("email", person.getEmail());
                    persons.add(personJSON);
                    break;
                }
            }

            System.out.println(obj);
            System.out.println(persons);

            obj.put("persons", persons);

            System.out.println(obj.toJSONString());

            setPersonsJSON(obj.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long personId) {
        try {
            JSONObject obj = getJSON();

            JSONArray persons = (JSONArray) obj.get("persons");

            Iterator<Map<String, Object>> iterator = persons.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> person = iterator.next();
                Long id = (Long) person.get("id");
                if (id != null && id.equals(personId)) {
                    iterator.remove();
                    break;
                }
            }

            obj.put("persons", persons);

            setPersonsJSON(obj.toJSONString());

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
