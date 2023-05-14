package test;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreateTestCaseBody;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class TrelloTestCard {

    static String token = "645c12a850d5fdbf2f1fbd5b%2FATTS52lJuzlkEGT1RkP4JzEDGTTwcDkEPoT0YTxkjMzOUQasV9hX5ADToCAflCSes3ocB891E9DA";
    static String idBoard = "645c13607c2dc8c246aa821d";
    static  String id;
    //  = "64614ba68f78823b66e4f059";

    @Test
    void createBoard() {

        Faker faker = new Faker();
        String testCaseName = faker.name().title();

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        step("Создать доску", () ->
                 given()
                        .log().uri()
                        .contentType(JSON)
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .post("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));


    }

    @Test
    void checkBoard1() {

        Response response =
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String id = response.path("id[0]");
        System.out.println(id);
    }
    @Test
    void checkBoard() {

        Response response =
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String id = response.path("id[0]");
        System.out.println(id);

        Faker faker = new Faker();
        String testCaseName = faker.name().title();

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        step("Создать доску", () ->
                given()
                        .log().uri()
                        .contentType(JSON)
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .put("https://trello.com/1/lists/" + id)
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));

    }
    @Test
    void changeName() {

        Response response =
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String id = response.path("id[0]");
        System.out.println(id);

        Faker faker = new Faker();
        String testCaseName = faker.name().title();

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        step("Создать доску", () ->
                given()
                        .log().uri()
                        .contentType(JSON)
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .put("https://trello.com/1/lists/" + id + "/closed")
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));
    }
}
