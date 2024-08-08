package yandex.praktikim;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.Before;

public class BaseTestClass {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = Parser.JSON; // если будет таймаут, а такое тут есть
    }

    @Step("Сравниваю код ответа с ожидаемым")
    public void compareStatusCode(Response response, int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }
}
