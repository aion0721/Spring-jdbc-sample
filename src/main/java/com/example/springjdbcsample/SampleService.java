package com.example.springjdbcsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Person> findAll() {
        String query = "SELECT * FROM person";
        List<Person> persons = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Person.class));
        return persons;
    }

    public Person save(Person person) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(person);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("person")
                .usingGeneratedKeyColumns("id");

        Number key = insert.executeAndReturnKey(param);
        person.setId(key.longValue());
        System.out.println("Add: " + person);
        return person;
    }

}