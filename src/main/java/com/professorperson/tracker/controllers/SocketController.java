package com.professorperson.tracker.controllers;

import com.professorperson.tracker.models.Message;
import com.professorperson.tracker.models.Task;
import com.professorperson.tracker.models.repos.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class SocketController {

    @Autowired
    TaskDAO taskDAO;

    @MessageMapping("/timer")
    @SendTo("/topic/timer")
    @ResponseBody
    public Message setTime(Message message) {
        int seconds = Integer.parseInt(message.getText());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String hours = df.format(new Date(seconds * 1000L));
        Task task = taskDAO.findById(Integer.parseInt(message.getTo())).get();
        task.setSeconds(seconds);
        task.setTime(hours);
        taskDAO.save(task);

        Message response = new Message();
        response.setText(hours);
        response.setTo(message.getTo());
        return response;
    }
}
