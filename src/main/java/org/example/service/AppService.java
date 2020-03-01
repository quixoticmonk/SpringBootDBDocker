package org.example.service;

import org.example.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {
    List<String> listofBooks = new ArrayList<>();

    @Autowired
    AppRepository appRepository;

    public List<String> returnBooks() {
        listofBooks.add("DevOps for a Modern Enterprise");
        return listofBooks;
    }

    public String returnBookById(String id) {
        return appRepository.getBookById(id);
    }
}
