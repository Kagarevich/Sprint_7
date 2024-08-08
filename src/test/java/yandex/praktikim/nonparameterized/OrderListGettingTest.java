package yandex.praktikim.nonparameterized;

import com.example.model.OrderList;
import com.example.repository.OrdersRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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
    @DisplayName("Проверка, что запрос списка заказов соответствует схеме в документации")
    @Description("Проверка того, чтобы данные успешно мэтчатся со схемой, которую я описал в java классе, а исходные" +
            "данные взял из документации")
    public void successRequestGetCorrectOrderList() {
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
    @DisplayName("Мэтчим данные - это и есть проверка")
    @Description("Мэтчим данные. Если замэтчатся успешно, то будет успех" +
            "Решил не проверять длину списка заказов, так как он может быть 0, если в бд пока ничего нет")
    public void dataMatching(Response response) {
        response.body().as(OrderList.class);
        //не знал, нуден ли тут конкретный ассерт, без него тоже будет падать и ничего не блочить при провале теста
    }
}
