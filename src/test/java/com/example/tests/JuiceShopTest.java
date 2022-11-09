package com.example.tests;

import com.example.models.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class JuiceShopTest extends BaseTest {

    private static  String userName=null;
    @Test
    public void createCustomerSuccessfully() {
        int UserId = given().spec(requestSpecification)
                .and()
                .contentType(ContentType.JSON)
                .body(customerBuilder())
                .post("api/users")
                .then()
                .assertThat().statusCode(201)
                .extract().path("data.id");

        given().spec(requestSpecification)
                .and()
                .body(securityAnswerBuilder(UserId, 1, "asdf"))
                .post("api/SecurityAnswers")
                .then()
                .assertThat().statusCode(201);
    }

    @Test
    public void performDefaultSearch() {
        getSearchResults(null)
                .then()
                .assertThat().statusCode(200)
                .body("status", is("success"))
                .body("data", is(not(empty())))
                .body("data[0].id", is(not(notANumber())))
                .body("data[0].image", containsString(".jpg"));
    }

    @Test
    public void addProductToCart() {
        Authentication authentication = login(getUserName(), "password")
                .then()
                .assertThat().statusCode(200)
                .extract().as(LoginResponse.class).getAuthentication();

        int productId = getSearchResults(null)
                .then()
                .assertThat().statusCode(200)
                .body("data", is(not(empty())))
                .extract().path("data[0].id");

        addProductToBasket(productId, authentication.getBid(), 1, authentication.getToken())
                .then()
                .assertThat().statusCode(200)
                .body("status", is("success"))
                .body("data.BasketId", is(String.valueOf(authentication.getBid())))
                .body("data.ProductId", is(productId))
                .body("data.quantity", is(1));
    }

    private Response addProductToBasket(int productId, int basketId, int quantity, String token) {
        JSONObject basketRequestBody = new JSONObject();
        basketRequestBody.put("ProductId", productId);
        basketRequestBody.put("BasketId", String.valueOf(basketId));
        basketRequestBody.put("quantity", quantity);

        return given().spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(basketRequestBody)
                .post("api/BasketItems/");
    }

    private Response login(String email, String password) {
        JSONObject loginRequestBody = new JSONObject();
        loginRequestBody.put("email", email);
        loginRequestBody.put("password", password);

        return given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(loginRequestBody)
                .post("rest/user/login");
    }

    private Response getSearchResults(String searchTerm) {
        return given().spec(requestSpecification)
                .when()
                .queryParam("q", searchTerm)
                .get("rest/products/search");
    }

    private SecurityAnswers securityAnswerBuilder(int userId, int questionId, String answer) {
        return SecurityAnswers.builder().answer(answer).SecurityQuestionId(questionId)
                .UserId(userId).build();
    }

    private String getUserName(){
        if (userName == null){
            userName =  faker.bothify("????##@gmail.com");
        }
        return userName;
    }
    private Customer customerBuilder() {
        return Customer.builder()
                .email(getUserName())
                .password("password")
                .passwordRepeat("password")
                .securityQuestion(SecurityQuestion.builder()
                        .createdAt("2020-05-03T08:51:58.696Z")
                        .updatedAt("2020-05-03T08:51:58.696Z")
                        .question("Your eldest siblings middle name?")
                        .id(2)
                        .build())
                .securityAnswer("asdf").build();
    }
}
