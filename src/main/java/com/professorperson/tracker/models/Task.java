package com.professorperson.tracker.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.professorperson.tracker.time.Timer;
import com.professorperson.tracker.web.I_WebSocket;

import javax.persistence.*;

@Entity
public class Task {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String title;
    private String description;
    private String status;
    private String time;

    @Column(columnDefinition = "integer")
    private int seconds;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    @JsonBackReference
    private Feature feature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
