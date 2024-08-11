package yandex.praktikim.nonparameterized;

import api.client.CourierClient;
import com.example.model.Courier;
import com.example.model.ResponseErrorBody;
import com.example.model.generator.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierCreationTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void init() {
        courierClient = new CourierClient();
        courier = CourierGenerator.create();
    }

    @After
    public void removeCourier() {
        courierClient.removeCourier(courier);
    }

    @Test
    @DisplayName("Проверяю тело ответа успешного запроса создания курьера")
    @Description("тут также проверяется step'ы 201й статус код + доступность сервера + тело ответа")
    public void successCreateCourierRequestBodyIsCorrect() {
        Response response = courierClient.courierCreate(courier, 201);
        courierClient.compareResponseBodyStatusCodeCreated(response); // вывел отдельным степом для наглядности
    }

    @Test
    @DisplayName("Тест, что нельзя создать двух одинаковых курьеров")
    @Description("проверка, что нельзя создать двух одинаковых курьеров + правильный статус код")
    public void cannotCreateDuplicateCourier() {
        courierClient.courierCreate(courier, 201); //создали первого
        courierClient.courierCreate(courier, 409); //попытка создать дубликат
    }

    @Test
    @DisplayName("Тест, что нельзя создать двух курьеров с одинаковыми логинами")
    @Description("эта проверка была выписана отдельной строкой в задании")
    public void cannotCreateDuplicateCourierLogin() {
        courierClient.courierCreate(courier, 201); //создали первого
        courierClient.courierCreate(new Courier(
                courier.getLogin(),
                "notSamePassword",
                "notSameFirstName"),
                409
        ); //попытка создать с таким же логином
    }

    //Тут будет ошибка, так как поле code в документации отсутствует, а фактически оно есть
    @Test
    @DisplayName("Проверяю тело ответа запроса создания курьера с ошибкой 409")
    @Description("вывел эту проверку в отдельный тест, чтобы было нагляднее")
    public void notSuccessCreateCourierResponseBodySameLogin() {
        courierClient.courierCreate(courier, 201); //создали первого
        Response response = courierClient.courierCreate(courier, 409); //вызываем 409 ошибку
        courierClient.compareResponseBodyError(
                response,
                new ResponseErrorBody("Этот логин уже используется. Попробуйте другой."));
    }

    //Одиночная проверка тела ответа с ошибкой 400 - она должна упасть, так как документация не совпадает с реализацией
    //Тут будет ошибка, так как поле code в документации отсутствует, а фактически оно есть

    @Test
    @DisplayName("Проверка тела ответа при статус коде 400 + корректность статус кода")
    @Description("Проверяю тело ответа для запроса создания курьера при ошибке 400")
    public void checkCreateCourierResponseBodyErrorBadRequest() {
        Response response = courierClient.courierCreate(new Courier(), 400);
        courierClient.compareResponseBodyError(
                response,
                new ResponseErrorBody("Недостаточно данных для создания учетной записи")
        );
    }
}
