package com.example.apiauth;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiAuthApplicationTests {

    @Test
    @Disabled("Feign Client URI issue in test environment")
    void contextLoads() {
    }

}
