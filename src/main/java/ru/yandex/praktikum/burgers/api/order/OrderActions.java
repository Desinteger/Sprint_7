package ru.yandex.praktikum.burgers.api.order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.burgers.api.BaseClient;
import static io.restassured.RestAssured.given;

public class OrderActions extends BaseClient {
    private static final String CREATE_ORDER_PATH = "/api/v1/orders";
    private static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then().log().all();
    }

    @Step("Получение списка всех заказов")
    public ValidatableResponse getOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(CREATE_ORDER_PATH)
                .then().log().all();
    }

    @Step("Отмена заказа по трек-номеру")
    public void cancelOrder(int trackNumber) {
        given()
                .header("Content-type", "application/json")
                .queryParam("track", trackNumber).
                when().
                put(CANCEL_ORDER_PATH).
                then().log().all();
    }
}
