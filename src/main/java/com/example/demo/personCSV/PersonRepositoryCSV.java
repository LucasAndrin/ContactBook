package com.example.demo.personCSV;

import com.example.demo.person.Person;
import com.example.demo.person.PersonRepositoryInterface;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryCSV implements PersonRepositoryInterface {

    private static final String fileName = "src/main/java/com/example/demo/personCSV/data.csv";


    /**
     * Retorna todos os registros dentro do CSV.
     *
     * @return List<Person>
     */
    @Override
    public List<Person> findAll() {

        List<Person> persons = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                Person person = new Person();
                String[] data = line.split(",");

                person.setId(Long.valueOf(data[0]));
                person.setName(data[1]);
                person.setEmail(data[2]);
                person.setTelephone(data[3]);
                person.setDob(LocalDate.parse(data[4]));

                persons.add(person);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    /**
     * Método responsável por salvar uma nova pessoa no CSV.
     *
     * @param person Person
     */
    @Override
    public void save(Person person) {
        File file = new File(fileName);

        try {
            FileWriter outputFile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputFile, ',', CSVWriter.NO_QUOTE_CHARACTER);

            String[] aPerson = convertPersonToArray(person);
            writer.writeNext(aPerson);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converte pessoa para um String Array.
     *
     * @param person Person
     * @return String[]
     */
    private String[] convertPersonToArray(Person person) {
        String[] result = new String[5];

        result[0] = String.valueOf(autoIncrementId());
        result[1] = person.getName();
        result[2] = person.getEmail();
        result[3] = person.getTelephone();
        result[4] = String.valueOf(person.getDob());

        return  result;
    }

    /**
     * Retorna um novo Id.
     *
     * @return Long
     */
    public Long autoIncrementId() {
        long nextId = 0L;

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                    if(Long.parseLong(data[0]) > nextId) {
                        nextId = Long.parseLong(data[0]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nextId + 1;
    }

    /**
     * Função que atualiza a pessoa com base no
     *
     * @param personId Long
     * @param person Person
     */
    @Override
    public void updatePerson(Long personId, Person person) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName), ',')) {
            List<String[]> csvBody = reader.readAll();

            for (int i = 0; i < csvBody.size(); i++) {
                if (Long.parseLong(csvBody.get(i)[0]) == personId) {
                    csvBody.get(i)[0] = String.valueOf(personId);
                    csvBody.get(i)[1] = person.getName();
                    csvBody.get(i)[2] = person.getEmail();
                    csvBody.get(i)[3] = person.getTelephone();
                    csvBody.get(i)[4] = String.valueOf(person.getDob());
                }
            }

            CSVWriter writer = new CSVWriter(new FileWriter(new File(fileName)), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método responsável por deletar pessoa pelo parâmetro informado.
     *
     * @param personId Long
     */
    @Override
    public void deleteById(Long personId) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName), ',')) {
            List<String[]> csvBody = reader.readAll();

            Iterator<String[]> iterator = csvBody.iterator();
            while (iterator.hasNext()) {
                String[] row = iterator.next();
                if (Long.parseLong(row[0]) == personId) {
                    iterator.remove();
                }
            }

            CSVWriter writer = new CSVWriter(new FileWriter(new File(fileName)) , ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Função que retorna uma pessoa específica.
     *
     * @param personId Long
     * @return Optional<Person>
     */
    @Override
    public Optional<Person> findById(Long personId) {
        Optional<Person> personOptional = null;

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (Long.parseLong(data[0]) == personId) {
                    Person person = new Person();

                    person.setId(Long.parseLong(data[0]));
                    person.setName(data[1]);
                    person.setEmail(data[2]);
                    person.setTelephone(data[3]);
                    person.setDob(LocalDate.parse(data[4]));

                    personOptional = Optional.of(person);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return personOptional;
    }
}
