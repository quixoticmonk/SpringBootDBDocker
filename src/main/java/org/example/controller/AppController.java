package org.example.controller;

import org.example.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Autowired
    private AppService appService ;

    @GetMapping("/")
    public String returnHelloWorld(){
        return "Hello World";
    }

    @GetMapping("/Books")
    public String returnBooks(){
        return appService.returnHello();
    }

}
