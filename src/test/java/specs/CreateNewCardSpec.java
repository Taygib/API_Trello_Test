package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import test.TestBase;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class CreateNewCardSpec extends TestBase {

    public static RequestSpecification requestResponseCreateNewCard = with()
            .filter(withCustomTemplates())
            .basePath("/1/board/");

    public static ResponseSpecification responseResponseCreateNewCard = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static RequestSpecification requestCreateNewCard = with()
            .log().uri()
            .contentType(JSON)
            .filter(withCustomTemplates())
            .contentType("application/json;charset=UTF-8");

    public static ResponseSpecification responseCreateNewCard = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("name", notNullValue())
            .expectBody("idList", notNullValue())
            .build();
}