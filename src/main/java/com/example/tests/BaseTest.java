package com.example.tests;

import com.example.mocks.WiremockHelper;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

public class BaseTest {

    static WiremockHelper wireMockServer = null;
    static RequestSpecification requestSpecification;
    static Faker faker;

    @BeforeAll
    public static void createRequestSpecification() {
        wireMockServer = new WiremockHelper(38081);
        faker = new Faker(new Locale("en-US"));
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://juice-shop.herokuapp.com/")
                .build();
    }
}
