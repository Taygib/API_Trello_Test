package test;

import com.github.javafaker.Faker;
import config.TrelloConfig;
import io.restassured.response.Response;
import models.CreateAndDeleteName;
import models.PutRenameList;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static specs.CheckListSpec.requestCheckList;
import static specs.CheckListSpec.responseCheckListStatus200;
import static specs.CreateCardSpec.*;
import static specs.CreateListsSpec.requestCreateLists;
import static specs.CreateListsSpec.responseCreateLists;
import static specs.CreateNewCardSpec.*;
import static specs.DeleteCardInListSpec.*;
import static specs.RenameListSpec.*;
import static org.assertj.core.api.Assertions.assertThat;


public class TrelloTestList {
    Faker faker = new Faker();
    private static TrelloConfig config = ConfigFactory.create(TrelloConfig.class, System.getProperties());

    @Test
    @Tag("List")
    @Tag("Board")
    void createLists() {

        String testCaseName = faker.name().title();

        PutRenameList testCaseBody = new PutRenameList();
        testCaseBody.setName(testCaseName);

        step("Создать список", () ->
                given(requestCreateLists)
                        .cookie("token", config.token())
                        .body(testCaseBody)
                        .post(config.idBoard() + "/lists")
                        .then()
                        .spec(responseCreateLists))
                .body("name", is(testCaseBody.getName()));
    }

    @Test
    @Tag("Board")
    @Tag("List")
    void renameList() {

        Response rename =
                step("ID для переименования списка", () ->
                        given(requestResponseRenameList)
                                .cookie("token", config.token())
                                .get(config.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseRenameList)
                                .extract()
                                .response());
        String id = rename.path("id[0]");

        PutRenameList renameList = new PutRenameList();
        renameList.setName("Communications Developer");

        step("Переименовать список", () ->
                given(requestRenameList)
                        .cookie("token", config.token())
                        .body(renameList)
                        .when()
                        .put(id)
                        .then()
                        .spec(responseRenameList))
                .body("name", equalTo("Communications Developer"));

        step("Проверка переименовании", () ->{
            assertThat(renameList.getName()).isEqualTo("Communications Developer");
        });
    }

    @Test
    @Tag("Board")
    @Tag("List")
    void checkList() {

        step("Проверить список", () ->
                given(requestCheckList)
                        .cookie("token", config.token())
                        .get(config.idBoard() + "/lists")
                        .then()
                        .spec(responseCheckListStatus200));
    }

    @Test
    @Tag("Card")
    @Tag("Board")
    void createCard() {

        Response forCreate =
                step("idList для создания карточки", () ->
                        given(requestResponseCreateCard)
                                .cookie("token", config.token())
                                .get(config.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseCreateCard)
                                .extract()
                                .response());
        String idList = forCreate.path("id[0]");

        String testCaseName = faker.name().title();

        CreateAndDeleteName testCaseBody = new CreateAndDeleteName();
        testCaseBody.setName(testCaseName);
        testCaseBody.setIdList(idList);

        step("Создать карточку", () ->
                given(requestCreateCard)
                        .cookie("token", config.token())
                        .body(testCaseBody)
                        .post("/1/cards")
                        .then()
                        .spec(responseCreateCard))
                .body("idList", is(idList));
    }

    @Test
    @Tag("Card")
    @Tag("Board")
    @Tag("deleteCard")
    void deleteCardInList() {

        Response forIdList =
                step("idList для карточки", () ->
                        given(requestResponseForIdList)
                                .cookie("token", config.token())
                                .get(config.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseForIdList)
                                .extract()
                                .response());
        String idList = forIdList.path("id[0]");

        Response forIdCard =
                step("idCard для удаления карточки", () ->
                        given(requestResponseForIdCard)
                                .cookie("token", config.token())
                                .get(idList + "/cards")
                                .then()
                                .spec(responseResponseForIdCard)
                                .extract()
                                .response());
        String idCard = forIdCard.path("id[0]");

        CreateAndDeleteName testCaseBody = new CreateAndDeleteName();
        testCaseBody.setIdList(idList);

        step("Удалить карточку", () ->
                given(requestDeleteCardInList)
                        .cookie("token", config.token())
                        .body(testCaseBody)
                        .delete(idCard)
                        .then()
                        .log().body()
                        .log().status()
                        .spec(responseDeleteCardInListStatus200));
    }

    @Test
    @Tag("Card")
    @Tag("Board")
    void createNewCard() {

        Response forIdList =
                step("idList для создания карточки", () ->
                        given(requestResponseCreateNewCard)
                                .cookie("token", config.token())
                                .get(config.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseCreateNewCard)
                                .extract()
                                .response());
        String idList = forIdList.path("id[0]");

        String testCaseName = faker.name().title();

        CreateAndDeleteName testCaseBody = new CreateAndDeleteName();
        testCaseBody.setName(testCaseName);
        testCaseBody.setIdList(idList);

        step("Создать New Карточку", () ->
                given(requestCreateNewCard)
                        .cookie("token", config.token())
                        .body(testCaseBody)
                        .post("/1/cards")
                        .then()
                        .spec(responseCreateNewCard))
                .body("idList", is(idList));
    }
}