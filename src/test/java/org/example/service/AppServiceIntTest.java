package org.example.service;

import org.example.repository.AppRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AppServiceIntTest {

    @Autowired
    AppService appService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AppRepository appRepository;

    @Test
    void shouldReturnABook() {
        when(appRepository.getBookById(any())).thenReturn("DevOps for a Modern Enterprise");
        appService.returnBookById("1");
        verify(appRepository, times(1)).getBookById(any());
    }

}
