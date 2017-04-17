package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by forestnewark on 4/13/17.
 */
@Component
public class PeopleRepository {

    @Autowired
    JdbcTemplate template;

    public List<Person> listPeople(String search){
        return template.query("Select * FROM person WHERE lower(firstname) LIKE lower(?) OR lower(lastname) LIKE lower(?) LIMIT 200",
                new Object[] {"%" + search + "%", "%" + search + "%"},
                (resultSet, i) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")

                ));
    }

    public List<Person> listPeopleFirstName(String search){
        return template.query("Select * FROM person WHERE lower(firstname) LIKE lower(?) OR lower(lastname) LIKE lower(?) ORDER BY firstname ASC LIMIT 200",
                new Object[] {"%" + search + "%", "%" + search + "%"},
                (resultSet, i) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")

                ));
    }

    public List<Person> listPeopleLastName(String search){
        return template.query("Select * FROM person WHERE lower(firstname) LIKE lower(?) OR lower(lastname) LIKE lower(?) ORDER BY lastname ASC LIMIT 200",
                new Object[] {"%" + search + "%", "%" + search + "%"},
                (resultSet, i) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")

                ));
    }

    public Person getPerson(Integer personid){
        return template.queryForObject("Select * FROM person WHERE personid=?",

                new Object[]{personid},
                ((resultSet, i) -> new Person(
                        resultSet.getInt("personid"),
                        resultSet.getString("title"),
                        resultSet.getString("firstname"),
                        resultSet.getString("middlename"),
                        resultSet.getString("lastname"),
                        resultSet.getString("suffix")
                ))
                );
    }

    public void updatePerson(Person person){

        if(person.getPersonId() == null){
            template.update("INSERT INTO person (title, firstname, middlename, lastname, suffix) VALUES (?,?,?,?,?)",

                    person.getTitle(),person.getFirstName(),person.getMiddleName(),person.getLastName(),person.getSuffix());
        }else{
            template.update("UPDATE person SET title=?,firstname=?,middlename=?,lastname=?,suffix=? WHERE personid =?",
                    person.getTitle(),
                    person.getFirstName(),
                    person.getMiddleName(),
                    person.getLastName(),
                    person.getSuffix(),
                    person.getPersonId()
                    );

        }
    }

}
