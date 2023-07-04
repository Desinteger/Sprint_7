package ru.yandex.praktikum.burgers.api;

import org.junit.Test;
import org.junit.After;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.burgers.api.courier.Courier;
import ru.yandex.praktikum.burgers.api.courier.CourierActions;

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
                .statusCode(HTTP_OK)
                .and()
                .body("id", greaterThan(0));
    }

    @Test
    @DisplayName("Авторизация с несуществующим login")
      public void testAuthorisationWithNonexistentLogin() {
        courier.setLogin("Exodus");
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с пустым login")
      public void testAuthorisationWithNullLogin() {
        courier.setLogin(null);
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с некорректным password")
      public void testAuthorisationWithIncorrectPassword() {
        courier.setPassword("некорректныйпароль");
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с пустым password")
      public void testAuthorisationWithNullPassword() {
        courier.setPassword("");
        ValidatableResponse response = courierActions.loginCourier(courier);
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void deleteCourier() {
        CourierActions.deleteCourier();
    }
}
