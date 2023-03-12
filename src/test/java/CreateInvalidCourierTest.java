import base.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import pojo.Courier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateInvalidCourierTest {

    private CourierApi courierApi;
    private Courier courier;

    public CreateInvalidCourierTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Courier(null, "123456", "Invalid")},
                {new Courier("Invalid", null, "Invalid")}
        };
    }

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @Test
    @DisplayName("Создание курьера без одного из парамметров")
    public void checkCreateCourierWithoutLoginOrPassword() {
        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}