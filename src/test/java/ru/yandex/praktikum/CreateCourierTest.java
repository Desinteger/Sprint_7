package ru.yandex.praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.*;
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
                .body("ok",is(true))
                .and()
                .statusCode(HTTP_CREATED);
    }

    @Test
    @DisplayName("Создание курьера без поля firstName")
    public void testCreatingCourierWithoutFirstName(){
        courier = Courier.generateCourier();
        courier.setFirstName(null);
        ValidatableResponse response = courierActions.createCourier(courier);
        response.assertThat()
                .body("ok",is(true))
                .and()
                .statusCode(HTTP_CREATED);
    }

    @Test
    @DisplayName("Создание курьера без поля login")
    public void testCreatingCourierWithoutLogin() {
        courier = Courier.generateCourier();
        courier.setLogin(null);
        ValidatableResponse response = courierActions.createCourier(courier);
        response.assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера с существующим login")
    public void testCreatingCourierWithSameLogin() {
        courierActions.createCourier(Courier.generateCourier());
        ValidatableResponse response = courierActions.createCourier(Courier.generateCourier());
        response.assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(HTTP_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без поля password")
    public void testCreatingCourierWithoutPassword() {
        courier = Courier.generateCourier();
        courier.setPassword(null);
        ValidatableResponse response = courierActions.createCourier(courier);
        response.assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @After
    public void deleteCourier() {
        CourierActions.deleteCourier();
    }
}