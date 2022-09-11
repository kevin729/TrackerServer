package com.professorperson.tracker.controllers;

import com.professorperson.tracker.models.Feature;
import com.professorperson.tracker.models.Task;
import com.professorperson.tracker.models.repos.FeatureDAO;
import com.professorperson.tracker.models.repos.SprintDAO;
import com.professorperson.tracker.models.repos.TaskDAO;
import com.professorperson.tracker.time.Timer;
import com.professorperson.tracker.web.I_WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class SprintController {

    @Autowired
    SprintDAO sprints;

    @Autowired
    FeatureDAO features;

    @Autowired
    TaskDAO tasks;

    @Autowired
    I_WebSocket socket;

    @GetMapping("/features")
    public List<Feature> getFeatures() {
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

    @PostMapping("/track")
    public void track() {
        Timer timer = new Timer(socket);
        timer.start();
    }

    @PostMapping("/unTrack")
    public void unTrack() {

    }
}
