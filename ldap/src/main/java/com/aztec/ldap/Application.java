package com.aztec.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The main() method defers to the SpringApplication helper class, providing Application.class as 
 * an argument to its run() method. This tells Spring to read the annotation metadata from Application 
 * and to manage it as a component in the Spring application context.
 *
 * The @ComponentScan annotation tells Spring to search recursively through the hello package and its 
 * children for classes marked directly or indirectly with Spring's @Component annotation. This directive 
 * ensures that Spring finds and registers the WebSecurityConfig class, because it is marked with 
 * @Configuration, which in turn is a kind of @Component annotation.
 *
 * The @EnableAsync annotation switches on Spring's ability to run @Async methods in a background thread pool.
 *
 * The @EnableAutoConfiguration annotation switches on reasonable default behaviors based on the content of 
 * your classpath. For example, it looks for any class that implements the CommandLineRunner interface and 
 * invokes its run() method. In this case, it runs the demo code for this guide.
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
