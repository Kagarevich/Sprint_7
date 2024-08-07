package com.example.repository;

import com.example.model.Courier;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierRepository {
    public static Response courierCreate(Courier requestBody) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/v1/courier");
    }

    public static void courierRemove(String id) {
        given()
                .delete("/api/v1/courier/" + id);
    }

    public static Response courierLogin(Courier requestBody) {
        return given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier/login");
    }
}
