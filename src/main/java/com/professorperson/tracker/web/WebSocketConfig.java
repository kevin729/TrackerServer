package com.professorperson.tracker.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        StompWebSocketEndpointRegistration registration = registry.addEndpoint("/time"); //endpoint with JS fallback, uses the best available transport
        registration.setAllowedOriginPatterns("*");
        registration.withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); //define message mappings with annotation @MessageMapping /app/*
        registry.enableSimpleBroker("/topic"); //transfer messages back to clients prefixed with /topic
    }

    @Bean("timeSocket")
    public I_WebSocket getTimeSocket() {
        return new WebSocket("ws://tasktrackerserver.herokuapp.com/time");
    }

    @Bean("lukeMindSocket")
    public I_WebSocket getLukeMindSocket() {
        return new WebSocket("ws://lukemind.herokuapp.com/frame");
    }
}
