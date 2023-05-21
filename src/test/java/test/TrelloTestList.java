package test;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import models.CreateTestCaseBody;
import models.PutRenameList;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.commons.lang3.ClassUtils.getName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.*;

public class TrelloTestList {

    static String token = "645c12a850d5fdbf2f1fbd5b%2FATTS52lJuzlkEGT1RkP4JzEDGTTwcDkEPoT0YTxkjMzOUQasV9hX5ADToCAflCSes3ocB891E9DA";
    static String idBoard = "645c13607c2dc8c246aa821d";

    @Test
    @Tag("List")
    @Tag("Board")
    void createLists() {

        Faker faker = new Faker();
        String testCaseName = faker.name().title();

        PutRenameList testCaseBody = new PutRenameList();
        testCaseBody.setName(testCaseName);

        step("Создать список", () ->
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
                        .statusCode(200))
                .body("name", is(testCaseBody.getName()));
    }

    @Test
    @Tag("Board")
    @Tag("List")
    void renameList() {

        Response response =
                step("ID для переименования списка", () ->
                        given()
                                .cookie("token", token)
                                .filter(withCustomTemplates())
                                .get("https://trello.com/1/board/" + idBoard + "/lists")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response());
        String id = response.path("id[0]");

        PutRenameList renameList = new PutRenameList();
        renameList.setName("Communications Developer");

        step("Переименовать список", () ->
                given()
                        .log().uri()
                        .contentType(JSON)
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .contentType("application/json;charset=UTF-8")
                        .body(renameList)
                        .when()
                        .put("https://trello.com/1/lists/" + id)
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200))
                .body("name", is("Communications Developer"));
    }

    @Test
    @Tag("Board")
    @Tag("List")
    void checkList() {

        step("Проверить список", () ->
                given()
                        .cookie("token", token)
                        .filter(withCustomTemplates())
                        .get("https://trello.com/1/board/" + idBoard + "/lists")
                        .then()
                        .log().body()
                        .log().status()
                        .statusCode(200));
    }

    @Test
    @Tag("Card")
    @Tag("Board")
    void createCard() {

        Response response =
                step("idList для создания карточки", () ->
                        given()
                                .cookie("token", token)
                                .filter(withCustomTemplates())
                                .get("https://trello.com/1/board/" + idBoard + "/lists")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response());
        String idList = response.path("id[0]");

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
                        .statusCode(200))
                .body("idList", is(idList));
    }

    @Test
    @Tag("Card")
    @Tag("Board")
    @Tag("deleteCard")
    void deleteCardInList() {

        Response response =
                step("idList для карточки", () ->
                        given()
                                .cookie("token", token)
                                .filter(withCustomTemplates())
                                .get("https://trello.com/1/board/" + idBoard + "/lists")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response());
        String idList = response.path("id[0]");

        Response responseNext =
                step("idCard для удаления карточки", () ->
                        given()
                                .cookie("token", token)
                                .filter(withCustomTemplates())
                                .get("https://trello.com/1/lists/" + idList + "/cards")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response());
        String idCard = responseNext.path("id[0]");

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setIdList(idList);

        step("Удалить карточку", () ->
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
                step("idList для создания карточки", () ->
                        given()
                                .cookie("token", token)
                                .filter(withCustomTemplates())
                                .get("https://trello.com/1/board/" + idBoard + "/lists")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response());
        String idList = response.path("id[0]");

        Faker faker = new Faker();
        String testCaseName = faker.name().title();

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);
        testCaseBody.setIdList(idList);

        step("Создать NewКарточку", () ->
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
                        .statusCode(200))
                .body("idList", is(idList));
    }
}