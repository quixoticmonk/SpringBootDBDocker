package org.example.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppServiceTest {

    @Test
    void shouldHaveAMethodToPrintHello(){
        AppService appService= new AppService();
        assertThat(appService.returnHello()).isNotEmpty();
    }

}
