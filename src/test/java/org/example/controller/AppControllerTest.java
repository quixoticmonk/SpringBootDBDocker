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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
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
    void shouldCallService(){
        Mockito.when(appService.returnHello()).thenReturn("Hello");

        assertThat(appController.returnBooks()).isEqualTo("Hello");
        Mockito.verify(appService,times(1)).returnHello();
    }




}
