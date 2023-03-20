import base.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class OrdersTest{

    private OrderApi orderApi;

    @Before
    public void setUp() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Check get orders list")
    public void checkGetOrders() {
        orderApi.getOrdersList()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", notNullValue());

        int actualOrdersListSize = orderApi.getOrdersList()
                .extract().jsonPath()
                .getList("orders").size();

        assertEquals(30, actualOrdersListSize);
    }
}