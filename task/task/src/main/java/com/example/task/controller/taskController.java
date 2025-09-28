package com.example.task.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class taskController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
}