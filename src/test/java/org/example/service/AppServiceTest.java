package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class AppServiceTest {

    AppService appService;


    @BeforeEach
    void setup(){
        appService= new AppService();
    }

    @Test
    void shouldHaveAMethodToPrintHello(){
        assertThat(appService.returnBooks()).isNotEmpty();
    }

    @Test
    void shouldReturnList(){
        List<String> listofBooks = new ArrayList<>();
        listofBooks.add("DevOps for a Modern Enterprise");
        assertThat(appService.returnBooks()).isEqualTo(listofBooks);
    }



}
