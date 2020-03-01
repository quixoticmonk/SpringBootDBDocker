package org.example.controller;

import org.example.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {

    @Autowired
    private AppService appService ;

    @GetMapping("/")
    public String returnHelloWorld(){
        return "Hello World";
    }

    @GetMapping("/Books")
    public List<String> returnBooks(){
        return appService.returnBooks();
    }

    @GetMapping("/Books/{id}")
    public String returnBookById(@PathVariable("id")  String id){
        return appService.returnBookById(id);
    }
}
