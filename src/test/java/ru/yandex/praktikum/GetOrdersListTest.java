package ru.yandex.praktikum;

import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.junit4.DisplayName;
import static java.net.HttpURLConnection.HTTP_OK;

@DisplayName("Тесты: получение списка заказов")
public class GetOrdersListTest extends BasicTest {
private final OrderActions order = new OrderActions();

@Test
@DisplayName("Тест: получение списка заказов")
      public void testGettingOrdersList() {
            ValidatableResponse response = order.getOrders();
            response.assertThat()
                  .body("orders", hasSize(greaterThan(0)))
                  .statusCode(equalTo(HTTP_OK));
        }
}