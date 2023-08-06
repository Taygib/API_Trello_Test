package test;

import config.TrelloConfig;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import models.PutRenameList;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RenameListSpec.*;
import static specs.RenameListSpec.responseRenameList;

@Owner("Taygib")
@Tag("Board")
public class PutRenameListTest {
    private static TrelloConfig trelloConfig = ConfigFactory.create(TrelloConfig.class, System.getProperties());

    @Test
    @Tag("List")
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
}