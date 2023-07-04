package ru.yandex.praktikum.burgers.api;

import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.junit4.DisplayName;
import ru.yandex.praktikum.burgers.api.order.OrderActions;

import static java.net.HttpURLConnection.HTTP_OK;

@DisplayName("Тесты: получение списка заказов")
public class GetOrdersListTest extends BasicTest {
private final OrderActions order = new OrderActions();

@Test
@DisplayName("Тест: получение списка заказов")
      public void testGettingOrdersList() {
            ValidatableResponse response = order.getOrders();
            response.assertThat()
                    .statusCode(equalTo(HTTP_OK))
                    .body("orders", hasSize(greaterThan(0)));
        }
}