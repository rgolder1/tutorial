package com.aztec.websockets;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main() method defers to the SpringApplication helper class, providing 
 * Application.class as an argument to its run() method. This tells Spring to read 
 * the annotation metadata from Application and to manage it as a component in the 
 * Spring application context.
 * 
 * The @ComponentScan annotation tells Spring to search recursively through the 
 * com.aztec.websockets package and its children for classes marked directly or 
 * indirectly with Spring's @Component annotation. This directive ensures that 
 * Spring finds and registers the GreetingController, because it is marked with 
 * @Controller, which in turn is a kind of @Component annotation.
 * 
 * The @EnableAutoConfiguration annotation switches on reasonable default behaviors 
 * based on the content of your classpath. For example, because the application 
 * depends on the embeddable version of Tomcat (tomcat-embed-core.jar), a Tomcat 
 * server is set up and configured with reasonable defaults on your behalf. And 
 * because the application also depends on Spring MVC (spring-webmvc.jar), a Spring 
 * MVC DispatcherServlet is configured and registered for you - no web.xml necessary! 
 * Auto-configuration is a powerful, flexible mechanism.
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
