package ru.yandex.praktikum.burgers.api;
import io.restassured.RestAssured;
import org.junit.Before;

public class BasicTest {

    @Before
    public void setUp() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }
}
