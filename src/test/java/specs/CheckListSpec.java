package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static org.hamcrest.Matchers.notNullValue;

public class CheckListSpec {

    public static RequestSpecification requestCheckList = with()
            .filter(withCustomTemplates())
            .baseUri("https://trello.com")
            .basePath("/1/board/");

    public static ResponseSpecification responseCheckListStatus200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("name",notNullValue())
            .build();
}