package com.aztec.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The Receiver is a simple POJO that defines a method for receiving messages. When you register it to
 * receive messages, you can name it anything you want.
 * 
 * For convenience, this POJO also has an autowired AnnotationConfigApplicationContext. This empowers 
 * it to shutdown the app once the message is receive. This is something you are not likely to 
 * implement in a production application.
 */
public class Receiver {

	@Autowired
	AnnotationConfigApplicationContext context;

	public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        this.context.close();
    }
}
