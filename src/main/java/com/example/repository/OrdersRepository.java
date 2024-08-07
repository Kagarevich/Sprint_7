package com.example.repository;

import com.example.model.Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

//Так как в документации для одного Order не было отдельного раздела документации, то я
//поместил все запросы в Orders(в документации тоже название Orders)
public class OrdersRepository {
    public static Response orderListGet() {
        return given()
                .get("/api/v1/orders");
    }

    public static Response orderCreate(Order requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/v1/orders");
    }
}
