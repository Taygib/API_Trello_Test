package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import test.TestBase;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class DeleteCardInListSpec extends TestBase {

    public static RequestSpecification requestResponseForIdList = with()
            .filter(withCustomTemplates())
            .basePath("/1/board/");

    public static ResponseSpecification responseResponseForIdList = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static RequestSpecification requestResponseForIdCard = with()
            .filter(withCustomTemplates())
            .basePath("/1/lists/");

    public static ResponseSpecification responseResponseForIdCard = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static RequestSpecification requestDeleteCardInList = with()
            .filter(withCustomTemplates())
            .basePath("/1/cards/");

    public static ResponseSpecification responseDeleteCardInListStatus200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();
}