package com.aztec.transactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use the BookingService class to create a JDBC-based service that books people into the system by name.
 * 
 * The code has an autowired JdbcTemplate, a handy template class that does all the database interactions 
 * needed by the code below.
 * 
 * You also have a book method aimed at booking multiple people. It loops through the list of people, and 
 * for each person, inserts them into the BOOKINGS table using the JdbcTemplate. This method is tagged 
 * with @Transactional, meaning that any failure causes the entire operation to roll back to its previous 
 * state, and to re-throw the original exception. This means that none of the people will be added to 
 * BOOKINGS if one person fails to be added.
 * 
 * You also have a findAllBookings method to query the database. Each row fetched from the database is 
 * converted into a String and then assembled into a List.
 */
public class BookingService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void book(String... persons) {
        for (String person : persons) {
            System.out.println("Booking " + person + " in a seat...");
            jdbcTemplate.update("insert into BOOKINGS(FIRST_NAME) values (?)", person);
        }
    };

    public List<String> findAllBookings() {
        return jdbcTemplate.query("select FIRST_NAME from BOOKINGS", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("FIRST_NAME");
            }
        });
    }

}
