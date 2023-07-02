package ru.yandex.praktikum;

import org.junit.Test;
import org.junit.After;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

@DisplayName("Тесты: авторизация курьера")
public class LoginCourierTest extends BasicTest {
    static CourierActions courierActions = new CourierActions();
    Courier courier = Courier.generateCourier();

    @Test
    @DisplayName("Авторизация с валидными login и password")
      public void testAuthorisationWithCorrectLoginAndPassword() {
        courierActions.createCourier(Courier.generateCourier());
        ValidatableResponse response = courierActions.loginCourier(Courier.generateCourier());
        response.assertThat()
                .body("id", greaterThan(0))
                .and()
                .statusCode(HTTP_OK);
    }

    @Test
    @DisplayName("Авторизация с несуществующим login")
      public void testAuthorisationWithNonexistentLogin() {
        courier.setLogin("Exodus");
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с пустым login")
      public void testAuthorisationWithNullLogin() {
        courier.setLogin(null);
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация с некорректным password")
      public void testAuthorisationWithIncorrectPassword() {
        courier.setPassword("некорректныйпароль");
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с пустым password")
      public void testAuthorisationWithNullPassword() {
        courier.setPassword("");
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @After
    public void deleteCourier() {
        CourierActions.deleteCourier();
    }
}
