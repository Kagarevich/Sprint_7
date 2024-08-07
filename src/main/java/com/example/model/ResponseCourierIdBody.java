package com.example.model;

public class ResponseCourierIdBody {
    private Integer id;

    public ResponseCourierIdBody(Integer id) {
        this.id = id;
    }

    //в уроке было написано, что нужен для работы сериализации
    public ResponseCourierIdBody() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
