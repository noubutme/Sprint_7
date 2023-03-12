import base.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import pojo.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest{

    private OrderApi orderApi;
    private Order order;
    private ValidatableResponse response;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Order("Alex", "Alex", "Kuku, kuka", "Butovo",
                        "+7 800 555 35 35", "6", "2023-02-02",
                        "Nice day", List.of())},
                {new Order("Kok", "Kok", "Moscow, area", "Any",
                        "+7 800 355 35 36", "6", "2023-01-01",
                        "You nice", List.of("BLACK"))},
                {new Order("Nikita", "Yana", "Petr,84", "Green",
                        "+7 800 355 35 37",
                        "6", "2023-01-01",
                        "Nice of to see your", List.of("BLACK", "GREY"))},
        };
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Check create orders")
    public void checkCreateOrder() {
        response = orderApi.create(order)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }

    @After
    public void deleteOrder() {
        Integer track = response.extract().path("track");

        orderApi.delete(track);
    }
}