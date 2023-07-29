package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class CreateListsSpec {

    public static RequestSpecification requestCreateLists = with()
            .log().uri()
            .contentType(JSON)
            .filter(withCustomTemplates())
            .contentType("application/json;charset=UTF-8")
            .baseUri("https://trello.com")
            .basePath("/1/board/");

    public static ResponseSpecification responseCreateLists = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("name",notNullValue())
            .build();
}