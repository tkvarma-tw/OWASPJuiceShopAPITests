package com.example.tests;

import com.example.models.Customer;
import com.example.models.SecurityAnswers;
import com.example.models.SecurityQuestion;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class JuiceShopTest {

    private static RequestSpecification requestSpecification;
    private static Faker faker;

    @BeforeAll
    public static void createRequestSpecification() {
        faker = new Faker(new Locale("en-US"));
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://juice-shop.herokuapp.com/api/")
                .build();
    }

    @Test
    public void createCustomerSuccessfully() {
        getCustomer();
        int UserId = given().spec(requestSpecification)
                .and()
                .body(getCustomer())
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("data.id");

        given().spec(requestSpecification)
                .and()
                .body(getSecurityAnswers(UserId))
                .post("/SecurityAnswers")
                .then()
                .statusCode(201);
    }

    private SecurityAnswers getSecurityAnswers(int userId) {
        return SecurityAnswers.builder().answer("asdf").SecurityQuestionId(1)
                .UserId(userId)
                .build();
    }

    private Customer getCustomer() {
        return Customer.builder()
                .email(faker.bothify("????##@gmail.com"))
                .password("password")
                .passwordRepeat("password")
                .securityQuestion(SecurityQuestion.builder()
                        .createdAt("2020-05-03T08:51:58.696Z")
                        .updatedAt("2020-05-03T08:51:58.696Z")
                        .question("Your eldest siblings middle name?")
                        .build())
                .securityAnswer("asdf").build();
    }

}
