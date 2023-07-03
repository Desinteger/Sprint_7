package ru.yandex.praktikum.burgers.api;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class BaseClient {
    protected RequestSpecification getRequestSpecification() {
        return given()
                .log().all();
    }
}
