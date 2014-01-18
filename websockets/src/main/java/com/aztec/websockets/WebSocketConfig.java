package com.aztec.websockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketConfig is annotated with @Configuration to indicate that it is a Spring configuration 
 * class. It is also annotated @EnableWebSocketMessageBroker. As its name suggests, 
 * @EnableWebSocketMessageBroker enables WebSocket message handling, backed by a message broker.
 * 
 * The configureMessageBroker() method overrides the default method in WebSocketMessageBrokerConfigurer 
 * to configure the message broker. It starts by calling enableSimpleBroker() to enable a simple 
 * memory-based message broker to carry the greeting messages back to the client on destinations 
 * prefixed with "/topic". It also designates the "/app" prefix for messages that are bound for 
 * @MessageMapping-annotated methods.
 * 
 * The registerStompEndpoints() method registers the "/hello" endpoint, enabling SockJS fallback 
 * options so that alternative messaging options may be used if WebSocket is not available. This 
 * endpoint, when prefixed with "/app", is the endpoint that the GreetingController.greeting() 
 * method is mapped to handle.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/hello").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration channelRegistration) {
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration channelRegistration) {
	}

}
