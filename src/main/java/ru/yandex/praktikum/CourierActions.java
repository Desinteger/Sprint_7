package ru.yandex.praktikum;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;

public class CourierActions {
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";

    @Step("Cоздание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(courier).
                when().
                post(COURIER_LOGIN_PATH).
                then().log().all();
    }

    @Step("Удаление курьера")
    public static void deleteCourier() {
        Courier login = new Courier(Courier.generateCourier().getLogin(), Courier.generateCourier().getPassword());
        Response response =
                given()
                        .log().all()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post(COURIER_LOGIN_PATH);
        String id = response.jsonPath().getString("id");
        given()
                .log().all()
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all();
    }
}
