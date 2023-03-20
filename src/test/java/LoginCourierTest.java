import base.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import pojo.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest{

    private CourierApi courierApi;
    private Courier courier;
    private Integer courierId;

    @Before
    public void createCourier() {
        courierApi = new CourierApi();
        courier = new Courier("ninja-qa", "123", "saske");
        courierApi.create(courier);
    }

    @Test
    @DisplayName("Проверко корректности авторизации")
    public void checkLoginCourier() {
        courierApi.login(courier)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неправельными данными")
    public void checkIncorrectLoginCourier() {
        courierApi.login(new Courier(courier.getLogin(), "12"))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body( "message", equalTo("Учетная запись не найдена"));

        courierApi.login(new Courier("ninja", courier.getPassword()))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body( "message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void clearData() {
        courierId = courierApi.login(courier)
                .extract().path("id");

        courierApi.delete(courierId);
    }
}