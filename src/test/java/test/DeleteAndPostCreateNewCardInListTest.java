package test;

import config.TrelloConfig;
import io.qameta.allure.Owner;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.CreateAndDeleteName;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.CreateNewCardSpec.*;
import static specs.DeleteCardInListSpec.*;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Taygib")
@Tag("Board")
public class DeleteAndPostCreateNewCardInListTest extends TestBase {

    private static TrelloConfig trelloConfig = ConfigFactory.create(TrelloConfig.class, System.getProperties());

    @Test
    @Tag("Card")
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

    @Test
    @Tag("Card")
    @Tag("deleteCard")
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
}