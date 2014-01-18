package com.aztec.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Register the listener and send a message
 * 
 * Spring AMQP's RabbitTemplate provides everything you need to send and receive messages with RabbitMQ. Specifically, you need to configure:
 * 
 * - A message listener container
 * - Declare the queue, the exchange, and the binding between them
 * 
 * Spring Boot automatically creates a connection factory and a RabbitTemplate, reducing the amount of code you have to write.
 * 
 * You'll use RabbitTemplate to send messages, and you will register a Receiver with the message listener container to receive messages. 
 * The connection factory drives both, allowing them to connect to the RabbitMQ server.
 *
 * The bean defined in the listenerAdapter() method is registered as a message listener in the container defined in container(). It will 
 * listen for messages on the "chat" queue. Because the Receiver class is a POJO, it needs to be wrapped in the MessageListenerAdapter, 
 * where you specify it to invoke receiveMessage.
 * 
 * JMS queues and AMQP queues have different semantics. For example, JMS sends queued messages to only one consumer. While AMQP queues do 
 * the same thing, AMQP producers don't send messages directly to queues. Instead, a message is sent to an exchange, which can go to a 
 * single queue, or fanout to multiple queues, emulating the concept of JMS topics.
 * 
 * The message listener container and receiver beans are all you need to listen for messages. To send a message, you also need a Rabbit template.
 * 
 * The queue() method creates an AMQP queue. The exchange() method creates a topic exchange. The binding() method binds these two together, 
 * defining the behavior that occurs when RabbitTemplate publishes to an exchange.
 * 
 * Spring AMQP requires that the Queue, the TopicExchange, and the Binding be declared as top level Spring beans in order to be set up properly.
 * 
 * The main() method starts that process by creating a Spring application context. This starts the message listener container, which will start 
 * listening for messages. It then retrieves the RabbitTemplate from the application context, waits five seconds, and sends a "Hello from RabbitMQ!" 
 * message on the "chat" queue. Finally, the container closes the Spring application context and the application ends.
 */
@Configuration
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	final static String queueName = "spring-boot";

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ!");
    }
}
