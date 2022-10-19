package com.professorperson.tracker.controllers;

import com.google.gson.Gson;
import com.professorperson.tracker.models.Feature;
import com.professorperson.tracker.models.Message;
import com.professorperson.tracker.models.Task;
import com.professorperson.tracker.models.repos.FeatureDAO;
import com.professorperson.tracker.models.repos.SprintDAO;
import com.professorperson.tracker.models.repos.TaskDAO;
import com.professorperson.tracker.time.Timer;
import com.professorperson.tracker.web.I_WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1")
public class SprintController {

    List<Timer> timers = new ArrayList<>();

    @Autowired
    SprintDAO sprints;

    @Autowired
    FeatureDAO features;

    @Autowired
    TaskDAO tasks;

    @Autowired
    @Qualifier("timeSocket")
    I_WebSocket timeSocket;

    @Autowired
    @Qualifier("lukeMindSocket")
    I_WebSocket lukeMindSocket;

    @GetMapping("/features")
    public List<Feature> getFeatures() {



        try {
            features.findAll();
        } catch (Exception e) {
            Message message = new Message();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            e.printStackTrace(pw);
            message.setText(sw.toString());
            lukeMindSocket.send(message, "/app/send_message");
        }
        return features.findAll();
    }

    @PostMapping("/features")
    public List<Feature> postFeature(@RequestBody Feature feature) {
        features.save(feature);
        return features.findAll();
    }

    @PostMapping("/tasks")
    public List<Feature> postTask(@RequestBody Task task) {
        tasks.save(task);
        return features.findAll();
    }

    @PutMapping("/tasks")
    public void putTask(@RequestBody Task task) {
        tasks.save(task);
    }

    @PostMapping("/track/{id}")
    public void track(@PathVariable int id) {
        Timer timer = null;
        Task task = tasks.findById(id).get();
        List<Timer> timerList = timers.stream().filter(t -> t.getTo().equals(Integer.toString(id))).collect(Collectors.toList());


        if (timerList.isEmpty()) {
            timer = new Timer(timeSocket, Integer.toString(id));
            timer.setTo(Integer.toString(id));
            timer.setSeconds(task.getSeconds());
            timers.add(timer);
        } else {
            timer = timerList.get(0);
        }
        timer.start();
    }

    @PostMapping("/unTrack/{id}")
    public void unTrack(@PathVariable int id) {
        Timer timer = timers.stream().filter(t -> t.getTo().equals(Integer.toString(id))).collect(Collectors.toList()).get(0);
        System.out.println(timer);
        System.out.println(timer.isRunning());
        timer.stop();
    }
}
