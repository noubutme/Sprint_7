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
public class LoginCourierWithInvalidDataTest {

    private CourierApi courierApi;
    private Courier courier;

    public LoginCourierWithInvalidDataTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new Courier("", "123")},
                {new Courier("ninja-qa", "")}
        };
    }

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @Test
    @DisplayName("Авторизация без логина или пароля")
    public void checkLoginCourierWithoutLoginOrPassword() {
        courierApi = new CourierApi();

        courierApi.login(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}