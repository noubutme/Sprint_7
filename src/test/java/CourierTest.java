import base.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import pojo.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {

    private CourierApi courierApi;
    private Courier courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courierApi = new CourierApi();
        courier = new Courier("LOpaid", "123456", "LOpaid");
    }

    @Test
    @DisplayName("Создание курьера")
    public void checkCreateCourier() {
        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    public void checkCreateTwoCouriers() {
        Courier secondCourier = new Courier("LOpaid", "123456", "LOpaid");

        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_CREATED);

        courierApi.create(secondCourier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание двух курьеров с повторяющимся логином")
    public void checkCreateCourierRepeatedLogin() {
        courierApi.create(courier)
                .assertThat()
                .statusCode(SC_CREATED);

        Courier secondCourier = new Courier(courier.getLogin(), "123456", "LOpaid");

        courierApi.create(secondCourier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void clearData() {
        courierId = courierApi.login(courier)
                .extract().path("id");

        courierApi.delete(courierId);
    }
}