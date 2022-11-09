package com.example.tests;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

public class BaseTest {

    static RequestSpecification requestSpecification;
    static Faker faker;

    @BeforeAll
    public static void createRequestSpecification() {
        faker = new Faker(new Locale("en-US"));
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost:8084/")
                .build();
    }
}
