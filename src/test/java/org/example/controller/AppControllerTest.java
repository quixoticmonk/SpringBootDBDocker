package org.example.controller;


import org.example.service.AppService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void shouldHaveStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHaveEndpointWithBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Books"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCallServiceForAllBooks(){
        List<String> testResponseList = new ArrayList<>();
        testResponseList.add("Book1");
        testResponseList.add("Book2");
        testResponseList.add("Book3");
        when(appService.returnBooks()).thenReturn(testResponseList);

        List<String> responseList = Arrays.asList("Book1", "Book2", "Book3");
        assertThat(appController.returnBooks()).isEqualTo(responseList);
        verify(appService,times(1)).returnBooks();
    }

    @Test
    void shouldHaveEndpointWithOneBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Books/1"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldCallServiceForFindBookById(){
        String testResponse = "Book1";
        String id = "1";
        when(appService.returnBookById("1")).thenReturn(testResponse);
        assertThat(appController.returnBookById(id)).isEqualTo("Book1");
        verify(appService,times(1)).returnBookById("1");
    }


}
