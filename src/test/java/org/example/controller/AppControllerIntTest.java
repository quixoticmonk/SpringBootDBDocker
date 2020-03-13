package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AppControllerIntTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldHitActualEncpoints(){
        ResponseEntity<String> response=  restTemplate.getForEntity("/api/books", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[{\"id\":1,\"bookName\":\"Davinci Code\",\"authorName\":\"Dan Brown\"},{\"id\":2,\"bookName\":\"Istanbul\",\"authorName\":\"Orhan Pamuk\"}]");
    }

}
