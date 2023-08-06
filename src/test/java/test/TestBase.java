package test;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;

public class TestBase {
    Faker faker = new Faker();

    static {
        RestAssured.baseURI = "https://trello.com";
    }
}