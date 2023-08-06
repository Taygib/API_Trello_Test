package test;

import com.github.javafaker.Faker;
import config.TrelloConfig;
import io.qameta.allure.Owner;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreateAndDeleteName;
import models.PutRenameList;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
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
    private static TrelloConfig trelloConfig = ConfigFactory.create(TrelloConfig.class, System.getProperties());

    @Test
    @Tag("List")
    @Tag("Board")
    @Owner("Taygib")
    @DisplayName("Создать лист")
    void createLists() {

        String testCaseName = faker.name().title();

        PutRenameList testCaseBody = new PutRenameList();
        testCaseBody.setName(testCaseName);

        PutRenameList response =
                step("Создать список", () ->
                        given(requestCreateLists)
                                .cookie("token", trelloConfig.token())
                                .body(testCaseBody)
                                .post(trelloConfig.idBoard() + "/lists")
                                .then()
                                .spec(responseCreateLists)
                                .extract().as(PutRenameList.class));

        step("Проверка создании листа", () -> {
            assertThat(testCaseBody.getName()).isEqualTo(response.getName());
        });
    }

    @Test
    @Tag("Board")
    @Tag("List")
    @Owner("Taygib")
    @DisplayName("Переименовать лист")
    void renameList() {

        Response rename =
                step("ID для переименования списка", () ->
                        given(requestResponseRenameList)
                                .cookie("token", trelloConfig.token())
                                .get(trelloConfig.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseRenameList)
                                .extract()
                                .response());
        String id = rename.path("id[0]");

        PutRenameList renameList = new PutRenameList();
        renameList.setName("Communications Developer");

        PutRenameList response =
                step("Переименовать список в " + renameList.getName(), () ->
                        given(requestRenameList)
                                .cookie("token", trelloConfig.token())
                                .body(renameList)
                                .when()
                                .put(id)
                                .then()
                                .spec(responseRenameList)
                                .extract().as(PutRenameList.class));

        step("Проверка переименовании", () -> {
            assertThat(renameList.getName()).isEqualTo(response.getName());
        });
    }

    @Test
    @Tag("Board")
    @Tag("List")
    @Owner("Taygib")
    @DisplayName("Открыть переименованный лист с статусом 200")
    void checkList() {

        step("Проверить список", () ->
                given(requestCheckList)
                        .cookie("token", trelloConfig.token())
                        .get(trelloConfig.idBoard() + "/lists")
                        .then()
                        .spec(responseCheckListStatus200));
    }

    @Test
    @Tag("Card")
    @Tag("Board")
    @Owner("Taygib")
    @DisplayName("Создать карточку в листе")
    void createCard() {

        Response forCreate =
                step("idList для создания карточки", () ->
                        given(requestResponseCreateCard)
                                .cookie("token", trelloConfig.token())
                                .get(trelloConfig.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseCreateCard)
                                .extract()
                                .response());
        String idList = forCreate.path("id[0]");

        String testCaseName = faker.name().title();

        CreateAndDeleteName testCaseBody = new CreateAndDeleteName();
        testCaseBody.setName(testCaseName);
        testCaseBody.setIdList(idList);

        Response response =
                step("Создать карточку", () ->
                        given(requestCreateCard)
                                .cookie("token", trelloConfig.token())
                                .body(testCaseBody)
                                .post("/1/cards")
                                .then()
                                .spec(responseCreateCard)
                                .extract()
                                .response());
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.get("name");
        String list = jsonPath.get("idList");

        step("Проверка создании карточки", () -> {
            assertThat(testCaseBody.getName()).isEqualTo(name);
            assertThat(testCaseBody.getIdList()).isEqualTo(list);
        });
    }

    @Test
    @Tag("Card")
    @Tag("Board")
    @Tag("deleteCard")
    @Owner("Taygib")
    @DisplayName("Удалить карточку")
    void deleteCardInList() {

        Response forIdList =
                step("idList для карточки", () ->
                        given(requestResponseForIdList)
                                .cookie("token", trelloConfig.token())
                                .get(trelloConfig.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseForIdList)
                                .extract()
                                .response());
        String idList = forIdList.path("id[0]");

        Response forIdCard =
                step("idCard для удаления карточки", () ->
                        given(requestResponseForIdCard)
                                .cookie("token", trelloConfig.token())
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
                        .cookie("token", trelloConfig.token())
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
    @Owner("Taygib")
    @DisplayName("Создать новую карточку в листе")
    void createNewCard() {

        Response forIdList =
                step("idList для создания карточки", () ->
                        given(requestResponseCreateNewCard)
                                .cookie("token", trelloConfig.token())
                                .get(trelloConfig.idBoard() + "/lists")
                                .then()
                                .spec(responseResponseCreateNewCard)
                                .extract()
                                .response());
        String idList = forIdList.path("id[0]");

        String testCaseName = faker.name().title();

        CreateAndDeleteName testCaseBody = new CreateAndDeleteName();
        testCaseBody.setName(testCaseName);
        testCaseBody.setIdList(idList);

        Response response =
                step("Создать New Карточку", () ->
                        given(requestCreateNewCard)
                                .cookie("token", trelloConfig.token())
                                .body(testCaseBody)
                                .post("/1/cards")
                                .then()
                                .spec(responseCreateNewCard)
                                .extract()
                                .response());
        JsonPath jsonPath = response.jsonPath();
        String name = jsonPath.get("name");
        String list = jsonPath.get("idList");

        step("Проверка создании карточки", () -> {
            assertThat(testCaseBody.getName()).isEqualTo(name);
            assertThat(testCaseBody.getIdList()).isEqualTo(list);
        });
    }
}