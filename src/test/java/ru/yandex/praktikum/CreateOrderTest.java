package ru.yandex.praktikum;

import org.junit.Test;
import org.junit.After;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
@DisplayName("Тесты: создание заказа")
public class CreateOrderTest extends BasicTest {
        OrderActions orderActions = new OrderActions();
        private final String firstName;
        private final String lastName;
        private final String address;
        private final int metroStation;
        private final String phone;
        private final int rentTime;
        private final String deliveryDate;
        private final String comment;
        private final String[] color;
        int trackNumber;

        public CreateOrderTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.metroStation = metroStation;
            this.phone = phone;
            this.rentTime = rentTime;
            this.deliveryDate = deliveryDate;
            this.comment = comment;
            this.color = color;
        }

        @Parameterized.Parameters(name = "Цвет самоката. Тестовые данные: {0} {1} {2} {3}")
        public static Object[][] testData() {
            return new Object[][]{
                    {"Naruto", "Uzumaki", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2023-12-12", "Saske, come back to Konoha", new String[]{"BLACK"}},
                    {"Naruto", "Uzumaki", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2023-12-12", "Saske, come back to Konoha", new String[]{"GREY"}},
                    {"Naruto", "Uzumaki", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2023-12-12", "Saske, come back to Konoha", new String[]{"BLACK", "GREY"}},
                    {"Naruto", "Uzumaki", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2023-12-12", "Saske, come back to Konoha", new String[]{}},
            };
        }

        @Test
        @DisplayName("Создание заказа")
        public void testOrderCreating(){
            Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
            ValidatableResponse response = orderActions.createOrder(order);
            response.assertThat()
                    .body("track", greaterThan(0))
                    .and()
                    .statusCode(HTTP_CREATED);
            trackNumber = response.extract().path("track");
        }

        @After
        public void cancelOrder() {
            orderActions.cancelOrder(trackNumber);
        }
}
