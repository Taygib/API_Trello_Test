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
import static org.assertj.core.api.Assertions.assertThat;
import static specs.CreateCardSpec.*;
import static specs.CreateCardSpec.responseCreateCard;

@Owner("Taygib")
@Tag("Board")
public class PostCreateCardTest extends TestBase {

    private static TrelloConfig trelloConfig = ConfigFactory.create(TrelloConfig.class, System.getProperties());

    @Test
    @Tag("Card")
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
}