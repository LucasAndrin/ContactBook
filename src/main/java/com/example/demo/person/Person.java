package com.example.demo.person;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Entity
@Table
public class Person {

    @Id
    @SequenceGenerator(
            name         = "person_sequence",
            sequenceName = "person_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy  = GenerationType.SEQUENCE,
            generator = "person_sequence"
    )
    @Column(
            updatable = false,
            nullable = false
    )
    private Long id;
    private String name;
    private LocalDate dob;
    private String telephone;

    private Integer age;
    private String email;

    public Person() {

    }

    public Person(String name, LocalDate dob, String telephone, Integer age, String email) {
        this.name = name;
        this.dob = dob;
        this.telephone = telephone;
        this.age = age;
        this.email = email;
    }

    public Person(Long id, String name, LocalDate dob, String telephone, String email) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.telephone = telephone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(getDob(), LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(dob, person.dob) && Objects.equals(telephone, person.telephone) && Objects.equals(age, person.age) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dob, telephone, age, email);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", dob=").append(dob);
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", email='").append(email).append('\'');
        return sb.toString();
    }
}
