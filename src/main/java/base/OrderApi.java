package base;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.Order;

import static io.restassured.RestAssured.given;

public class OrderApi extends ScooterRestClient {

    private static final String ORDERS_URI = BASE_URI + "orders/";

    @Step("Get orders list")
    public ValidatableResponse getOrdersList() {
        return given()
                .spec(getReqSpec())
                .get(ORDERS_URI)
                .then();
    }

    @Step("Create order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getReqSpec())
                .body(order)
                .post(ORDERS_URI)
                .then();
    }

    @Step("Delete order with track")
    public ValidatableResponse delete(int track) {
        return given()
                .spec(getReqSpec())
                .when()
                .put(ORDERS_URI + "/cancel?track={track}", track)
                .then();
    }
}