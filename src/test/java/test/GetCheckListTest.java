package test;

import config.TrelloConfig;
import io.qameta.allure.Owner;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.CheckListSpec.requestCheckList;
import static specs.CheckListSpec.responseCheckListStatus200;

@Owner("Taygib")
@Tag("Board")
public class GetCheckListTest {
    private static TrelloConfig trelloConfig = ConfigFactory.create(TrelloConfig.class, System.getProperties());

    @Test
    @Tag("List")
    @DisplayName("Открыть переименованный лист с статусом 200")
    void checkList() {

        step("Проверить список", () ->
                given(requestCheckList)
                        .cookie("token", trelloConfig.token())
                        .get(trelloConfig.idBoard() + "/lists")
                        .then()
                        .spec(responseCheckListStatus200));
    }
}