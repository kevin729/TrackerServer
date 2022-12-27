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

import java.util.ArrayList;
import java.util.List;
import java.util.*;
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
        List<Feature> featureList = features.findAll();

        if (featureList.isEmpty()) {
            return null;
        }

        featureList.stream().forEach(feature -> {
            feature.setTasks(feature.getTasks().stream().sorted(Comparator.comparingInt(Task::getId)).collect(Collectors.toCollection(LinkedHashSet::new)));
        });


        return featureList;
    }

    @PostMapping("/features")
    public List<Feature> postFeature(@RequestBody Feature feature) {
        features.save(feature);
        return features.findAll();
    }

    @DeleteMapping("/features/{id}")
    public List<Feature> deleteFeature(@PathVariable int id) {
        Feature feature = features.findById(id).get();
        for (Task task : feature.getTasks()) {
            tasks.delete(task);
        }

        features.delete(feature);
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
        timer.stop();
    }

    @DeleteMapping("/tasks/{id}")
    public List<Feature> deleteTask(@PathVariable int id) {
        Task task = tasks.findById(id).get();
        tasks.delete(task);
        return features.findAll();
    }
}
