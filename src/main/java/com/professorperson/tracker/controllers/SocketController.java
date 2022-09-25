package com.professorperson.tracker.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class SocketController {
    @MessageMapping("/timer")
    @SendTo("/topic/timer")
    @ResponseBody
    public String setTime(String message) {
        int seconds = Integer.parseInt(message);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        String hours = df.format(new Date(seconds * 1000L));
        return hours;
    }
}
