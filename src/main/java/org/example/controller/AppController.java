package org.example.controller;

import org.example.model.Book;
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

    @GetMapping("/Books")
    public List<Book> returnBooks(){
        return appService.returnBooks();
    }

    @GetMapping("/Books/{id}")
    public Book returnBook(@PathVariable("id") final String id){
        return appService.returnBook(id);
    }

}
