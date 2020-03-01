package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String returnHelloWorld(){
        return "Hello World";
    }

    @GetMapping("/Books")
    public String returnBooks(){
        return "Hello World";
    }

}
