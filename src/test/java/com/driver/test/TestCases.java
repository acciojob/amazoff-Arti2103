package com.driver.test;

import com.driver.Application;

import com.driver.DeliveryPartner;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(OrderAnnotation.class)
public class TestCases {



    @BeforeEach
    public void setUp() {
        // Initialize the controller

    }


}
