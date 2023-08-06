package test;

import config.TrelloConfig;
import io.qameta.allure.Owner;
import models.PutRenameList;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.CreateListsSpec.requestCreateLists;
import static specs.CreateListsSpec.responseCreateLists;

@Owner("Taygib")
@Tag("Board")
public class PostCreateListsTest extends TestBase {

    private static TrelloConfig trelloConfig = ConfigFactory.create(TrelloConfig.class, System.getProperties());

    @Test
    @Tag("List")
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
}