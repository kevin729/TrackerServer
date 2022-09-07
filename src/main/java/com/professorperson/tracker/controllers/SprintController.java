package com.professorperson.tracker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("v1")
public class SprintController {

    @GetMapping("/tasks")
    public List<String> getTasks() {
        return Arrays.asList(new String[] {"test 1", "test 2"});
    }
}
