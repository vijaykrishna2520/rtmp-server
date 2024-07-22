package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RTMPStreamController {

    @GetMapping("/")
    public String sample(){
        return "Hello world";
    }
}

