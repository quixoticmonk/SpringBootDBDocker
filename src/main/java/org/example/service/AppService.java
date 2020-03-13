package org.example.service;

import org.example.model.Book;
import org.example.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppService {

    @Autowired
    AppRepository appRepository;

    public List<Book> returnBooks() {
        return (List<Book>) appRepository.findAll();
    }

    public Book returnBook(String bookName) {
        return appRepository.findBookByBookName(bookName);
    }
}
