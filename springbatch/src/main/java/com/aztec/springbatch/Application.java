package com.aztec.springbatch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Although batch processing can be embedded in web apps and WAR files, the simpler 
 * approach demonstrated below creates a standalone application. You package everything 
 * in a single, executable JAR file, driven by a good old Java main() method.
 * 
 * The main() method defers to the SpringApplication helper class, providing 
 * Application.class as an argument to its run() method. This tells Spring to read the 
 * annotation metadata from Application and to manage it as a component in the Spring 
 * application context.
 * 
 * The @ComponentScan annotation tells Spring to search recursively through the 
 * com.aztec.springbatch package and its children for classes marked directly or indirectly 
 * with Spring's @Component annotation. This directive ensures that Spring finds and registers 
 * BatchConfiguration, because it is marked with @Configuration, which in turn is a kind of 
 * @Component annotation.
 * 
 * The @EnableAutoConfiguration annotation switches on reasonable default behaviours based on 
 * the content of your classpath. For example, it looks for any class that implements the 
 * CommandLineRunner interface and invokes its run() method. In this case, it runs the demo 
 * code for this guide.
 * 
 * For demonstration purposes, there is code to create a JdbcTemplate, query the database, 
 * and print out the names of people the batch job inserts.
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        List<Person> results = ctx.getBean(JdbcTemplate.class).query("SELECT first_name, last_name FROM people", new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet rs, int row) throws SQLException {
                return new Person(rs.getString(1), rs.getString(2));
            }
        });

        for (Person person : results) {
            System.out.println("Found <" + person + "> in the database.");
        }
    }

}
