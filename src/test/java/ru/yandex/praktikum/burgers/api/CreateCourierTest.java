package ru.yandex.praktikum.burgers.api;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.*;
import ru.yandex.praktikum.burgers.api.courier.Courier;
import ru.yandex.praktikum.burgers.api.courier.CourierActions;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тесты: создание курьера")
public class CreateCourierTest extends BasicTest {

    CourierActions courierActions = new CourierActions();
    private Courier courier;

    @Test
    @DisplayName("Создание курьера со всеми валидными полями")
    public void testCreatingCourierWithFullValidFields(){
        ValidatableResponse response = courierActions.createCourier(Courier.generateCourier());
        response.assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .body("ok",is(true));
    }

    @Test
    @DisplayName("Создание курьера без поля firstName")
    public void testCreatingCourierWithoutFirstName(){
        courier = Courier.generateCourier();
        courier.setFirstName(null);
        ValidatableResponse response = courierActions.createCourier(courier);
        response.assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .body("ok",is(true));

    }

    @Test
    @DisplayName("Создание курьера без поля login")
    public void testCreatingCourierWithoutLogin() {
        courier = Courier.generateCourier();
        courier.setLogin(null);
        ValidatableResponse response = courierActions.createCourier(courier);
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера с существующим login")
    public void testCreatingCourierWithSameLogin() {
        courierActions.createCourier(Courier.generateCourier());
        ValidatableResponse response = courierActions.createCourier(Courier.generateCourier());
        response.assertThat()
                .statusCode(HTTP_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без поля password")
    public void testCreatingCourierWithoutPassword() {
        courier = Courier.generateCourier();
        courier.setPassword(null);
        ValidatableResponse response = courierActions.createCourier(courier);
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        CourierActions.deleteCourier();
    }
}