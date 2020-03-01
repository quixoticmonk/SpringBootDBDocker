package org.example.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

    @Autowired
    MockMvc mockMvc;

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



}
