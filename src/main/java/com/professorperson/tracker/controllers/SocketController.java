package com.professorperson.tracker.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SocketController {
    @MessageMapping("/timer")
    @SendTo("/topic/timer")
    @ResponseBody
    public String setTime(String message) {
        return message;
    }
}
