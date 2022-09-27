package com.professorperson.tracker.web;

import com.google.gson.Gson;
import com.professorperson.tracker.models.Message;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WebSocket implements I_WebSocket {

    private String url;
    private StompSession session;

    public WebSocket(String url) {
        this.url = url;
    }

    private class Handler extends StompSessionHandlerAdapter {

        private String message;

        public Handler(String message) {
            this.message = message;
        }

        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
        }
    }

    public StompSession setupSession(String message) {
        try {
            Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
            List<Transport> transports = Collections.singletonList(webSocketTransport);

            SockJsClient sockJsClient = new SockJsClient(transports);
            sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

            WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

            int port = -1;
            String uriParams = "";
            if (url.contains("localhost")) {
                port = 8080;
                uriParams = "localhost";
            } else {
                port = 443;
                uriParams = "herokuapp";
            }

            return stompClient.connect(url, new WebSocketHttpHeaders(), new Handler(message), uriParams, port).get();
        } catch (ExecutionException e) {

        } catch (InterruptedException e) {

        }

        return null;
    }

    @Override
    public void send(String message, String path) {
        if (session == null) {
            session = setupSession(message);
        }

        if (session == null || !session.isConnected()) {
            return;
        }

        session.send(path, message.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void send(Message message, String path) {
        if (session == null) {
            session = setupSession(message.getText());
        }

        if (session == null || !session.isConnected()) {
            return;
        }

        session.send(path, new Gson().toJson(message).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void sendAndPrint(String message, String path) {
        System.out.println(message);
        send(message, path);
    }
}
