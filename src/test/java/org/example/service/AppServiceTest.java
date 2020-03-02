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
    @DisplayName("Should Return List of Books")
    void shouldReturnBooks(){
        assertThat(appService.returnBooks()).isNotEmpty();
    }


}
