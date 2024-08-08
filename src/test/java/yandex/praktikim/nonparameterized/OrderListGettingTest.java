package yandex.praktikim.nonparameterized;

import com.example.model.OrderList;
import com.example.repository.OrdersRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import yandex.praktikim.BaseTestClass;

//Не тестил тут ошибки, так как об этом в задании не было, а с курьером это указывалось в явной форме
public class OrderListGettingTest extends BaseTestClass {

    @Test
    @DisplayName("Проверка, что запрос списка заказов возвращает статус код 200")
    @Description("В задании не были обозначены query параметры, а в доп задании обозначены," +
            "из чего делается вывод, что тут query параметры использовать не надо.")
    public void successRequestGetOKStatusCode() {
        Response response = sendGetRequestOrderList();
        compareStatusCode(response, 200);
    }

    @Test
    @DisplayName("Проверка, что ответ запроса списка заказов соответствует схеме в документации")
    @Description("Проверка того, чтобы данные успешно мэтчатся со схемой, которую я описал в java классе, а исходные" +
            "данные взял из документации")
    public void successOrderListGetRequestCorrectResponseBody() {
        Response response = sendGetRequestOrderList();
        dataMatching(response);
    }

    //Не стал выводить тут Step в отдельный класс, так как запрос тут всего один. Буду писать шаги тут :)
    @Step
    @DisplayName("Запрашиваем список заказов по умолчанию")
    @Description("Список заказов без параметров запроса - по умолчанию")
    public Response sendGetRequestOrderList() {
        return OrdersRepository.orderListGet();
    }

    @Step
    @DisplayName("Мэтчим данные. Можно ли тело ответа спарсить в список заказов")
    @Description("Смотрим, корректный ли ответ по содержанию." +
            "Я не смог найти подходящий мэтчер, а проверку на мэтчер необходима для проверки тела ответа." +
            "мэтчер any() не помог" +
            "Поэтому придумал такое решение.")
    public void dataMatching(Response response) {
        try {
            response.body().as(OrderList.class);
            Assert.assertTrue(true);
        } catch (Exception ex) {
            Assert.assertTrue("Не удалось спарсить данные, текст ошибки:\n" + ex.getMessage(), false);
        }
    }
}
