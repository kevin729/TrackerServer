package com.professorperson.tracker.web;

import com.professorperson.tracker.models.Message;

public interface I_WebSocket {
     int test = 1;

    void send(String message, String path);
    void send(Message message, String path);
    void sendAndPrint(String message, String path);
}
