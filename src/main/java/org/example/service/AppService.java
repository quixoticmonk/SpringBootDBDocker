package org.example.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {
    List<String> listofBooks = new ArrayList<>();
    public List<String> returnBooks() {
        listofBooks.add("DevOps for a Modern Enterprise");
        return listofBooks;
    }

    public String returnBookById(String id) {
        return "DevOps for a Modern Enterprise";
    }
}
