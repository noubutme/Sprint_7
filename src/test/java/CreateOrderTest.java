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
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35", "5", "2020-06-06",
                        "Saske, come back to Konoha", List.of())},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35", "5", "2020-06-06",
                        "Saske, come back to Konoha", List.of("BLACK"))},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35",
                        "5", "2020-06-06",
                        "Saske, come back to Konoha", List.of("BLACK", "GREY"))},
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