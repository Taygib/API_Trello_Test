package test;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import models.CreateTestCaseBody;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class TrelloTestList {

    static String token = "645c12a850d5fdbf2f1fbd5b%2FATTS52lJuzlkEGT1RkP4JzEDGTTwcDkEPoT0YTxkjMzOUQasV9hX5ADToCAflCSes3ocB891E9DA";
    static String idBoard = "645c13607c2dc8c246aa821d";

    @Test
    @Tag("List")
    @Tag("Board")
    void createLists() {

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
    @Tag("Board")
    @Tag("List")
    void renameList() {

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
        String name = response.path("name[0]");
        System.out.println(id + "\n" + name);

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
    @Tag("Board")
    @Tag("List")
    void checkList() {

        step("Открыть главную страницу", () ->
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @Tag("Card")
    @Tag("Board")
    void createCard() {

        Response response =
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String idList = response.path("id[0]");
        System.out.println(idList);

        Faker faker = new Faker();
        String testCaseName = faker.name().title();

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);
        testCaseBody.setIdList(idList);

        step("Создать карточку", () ->
                given()
                        .log().uri()
                        .contentType(JSON)
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .post("https://trello.com/1/cards")
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));
    }
    @Test
    @Tag("Card")
    @Tag("Board")
    void deleteCardInList() {

        Response response =
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String idList = response.path("id[0]");
        System.out.println(idList);

        Response responseNext =
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/lists/" + idList + "/cards")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String idCard = responseNext.path("id[0]");

        System.out.println(idCard);

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setIdList(idList);

        step("Открыть главную страницу", () ->
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .body(testCaseBody)
                        .delete("https://trello.com/1/cards/" + idCard)
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));
    }
    @Test
    @Tag("Card")
    @Tag("Board")
    void createNewCard() {

        Response response =
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        String idList = response.path("id[0]");
        System.out.println(idList);

        Faker faker = new Faker();
        String testCaseName = faker.name().title();

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);
        testCaseBody.setIdList(idList);

        step("Создать карточку", () ->
                given()
                        .log().uri()
                        .contentType(JSON)
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .post("https://trello.com/1/cards")
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));
    }
}
