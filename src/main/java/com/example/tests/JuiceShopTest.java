package com.example.tests;

import com.example.models.Customer;
import com.example.models.SearchResult;
import com.example.models.SecurityAnswers;
import com.example.models.SecurityQuestion;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;


public class JuiceShopTest extends BaseTest {

    @Test
    public void createCustomerSuccessfully() {
        getCustomer();
        int UserId = given().spec(requestSpecification)
                .and()
                .body(getCustomer())
                .post("api/users")
                .then()
                .statusCode(201)
                .extract()
                .path("data.id");

        given().spec(requestSpecification)
                .and()
                .body(getSecurityAnswers(UserId))
                .post("api/SecurityAnswers")
                .then()
                .statusCode(201);
    }

    @Test
    public void performDefaultSearch() {
        List<SearchResult> searchResults = given().spec(requestSpecification)
                .when()
                .queryParam("q", Collections.emptyList())
                .get("rest/products/search")
                .then()
                .statusCode(304)
                .body("status", is("success"))
                .extract().path("data");

        assertThat(searchResults).isNotEmpty();
        assertThat(searchResults.get(0).getPrice()).isPositive();
        assertThat(searchResults.get(0).getDeluxePrice()).isPositive();
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
