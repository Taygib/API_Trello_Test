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

public class SpecCreateCard {

    public static RequestSpecification requestResponseCreateCard = with()
            .filter(withCustomTemplates())
            .baseUri("https://trello.com")
            .basePath("/1/board/");

    public static ResponseSpecification responseResponseCreateCard = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static RequestSpecification requestCreateCard = with()
            .log().uri()
            .contentType(JSON)
            .filter(withCustomTemplates())
            .contentType("application/json;charset=UTF-8")
            .baseUri("https://trello.com");

    public static ResponseSpecification responseCreateCard = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("name",notNullValue())
            .expectBody("idList",notNullValue())
            .build();
}
