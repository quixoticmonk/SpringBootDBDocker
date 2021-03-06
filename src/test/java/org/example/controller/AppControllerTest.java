package org.example.controller;


import org.example.model.Book;
import org.example.service.AppService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AppService appService;

    @Autowired
    AppController appController;

    @Tag("unit")
    @Test
    void shouldHaveEndpointWithBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk());
    }

    @Tag("unit")
    @Test
    void shouldHaveEndpointForABook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Tag("unit")
    @Test
    @DisplayName("Should invoke Service Class for Books endpoint")
    void shouldCallServiceForAllBooks(){
        List<Book> booksList = new ArrayList<>();
        Book book = new Book(1,"","");
        booksList.add(book);
        when(appService.returnBooks()).thenReturn(booksList);
        assertThat(appController.returnBooks()).isEqualTo(booksList);
        verify(appService,times(1)).returnBooks();
    }

    @Tag("unit")
    @Test
    @DisplayName("Should invoke Service Class for/api/books/{id} endpoint")
    void shouldCallServiceForABookEndPoint(){
        Book book = new Book(1,"Istanbul","Orhan Pamuk");
        when(appService.returnBook(any())).thenReturn(book);
        assertThat(appController.returnBook("Istanbul").getAuthorName()).isEqualTo("Orhan Pamuk");
        verify(appService,times(1)).returnBook(any());
    }


}
