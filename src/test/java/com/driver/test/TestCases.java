package com.driver.test;

import com.driver.Application;
import com.driver.DeliveryPartner;
import com.driver.Order;
import com.driver.OrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {

    @BeforeEach
    void setup() {
        // This method runs before each test case
    }

    @Test
    void sampleTest() {
        // Sample test case
    }
}
