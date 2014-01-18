package com.aztec.transactions;

import javax.sql.DataSource;

import org.junit.Assert;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * You configure your beans in the Application configuration class. The bookingService method wires in 
 * an instance of BookingService.
 * 
 * As shown earlier in this guide, JdbcTemplate is autowired into BookingService, meaning you now need 
 * to define it in the Application code:
 * 
 * SimpleDriverDataSource is a convenience class and is not intended for production. For production, you 
 * usually want some sort of JDBC connection pool to handle multiple requests coming in simultaneously.
 * 
 * The jdbcTemplate method where you create an instance of JdbcTemplate also contains some DDL to declare 
 * the BOOKINGS table.
 * 
 * In production systems, database tables are usually declared outside the application.
 * 
 * The main() method defers to the SpringApplication helper class, providing Application.class as an 
 * argument to its run() method. This tells Spring to read the annotation metadata from Application and 
 * to manage it as a component in the Spring application context.
 * 
 * Note two especially valuable parts of this application configuration:
 * 
 * - @EnableTransactionManagement activates Spring's seamless transaction features, which makes 
 * @Transactional function.
 * - @EnableAutoConfiguration switches on reasonable default behaviors based on the content of your 
 * classpath. For example, it detects that you have spring-tx on the path as well as a DataSource, and 
 * automatically creates the other beans needed for transactions. Auto-configuration is a powerful, 
 * flexible mechanism.
 *
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
public class Application {

    @Bean
    BookingService bookingService() {
        return new BookingService();
    }

    @Bean
    DataSource dataSource() {
        return new SimpleDriverDataSource() {{
            setDriverClass(org.h2.Driver.class);
            setUsername("sa");
            setUrl("jdbc:h2:mem");
            setPassword("");
        }};
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("Creating tables");
        jdbcTemplate.execute("drop table BOOKINGS if exists");
        jdbcTemplate.execute("create table BOOKINGS(" +
                "ID serial, FIRST_NAME varchar(5) NOT NULL)");
        return jdbcTemplate;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        BookingService bookingService = ctx.getBean(BookingService.class);
        bookingService.book("Alice", "Bob", "Carol");
        Assert.assertEquals("First booking should work with no problem",
                3, bookingService.findAllBookings().size());

        try {
            bookingService.book("Chris", "Samuel");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        Assert.assertEquals("'Samuel' should have triggered a rollback",
                3, bookingService.findAllBookings().size());

        try {
            bookingService.book("Buddy", null);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        Assert.assertEquals("'null' should have triggered a rollback",
                3, bookingService.findAllBookings().size());

    }

}
