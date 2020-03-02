package org.example.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AppServiceTest {

    @Autowired
    private AppService appService;

    @Test
    @DisplayName("Response for returnBooks shouldn't be empty")
    void shouldReturnBooks(){
        assertThat(appService.returnBooks()).isNotEmpty();
    }

    @Test
    @DisplayName("ReturnBook shouldn't have an empty response")
    void shouldReturnABook(){
        assertThat(appService.returnBook(1)).isNotNull();
    }


}
