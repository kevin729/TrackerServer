package com.professorperson.tracker.web;

public interface I_WebSocket {
     int test = 1;

    void send(String message, String path);
    void sendAndPrint(String message, String path);
}
