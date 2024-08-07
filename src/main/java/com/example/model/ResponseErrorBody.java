package com.example.model;

public class ResponseErrorBody {
    private String message;

    public ResponseErrorBody(String message) {
        this.message = message;
    }

    //в уроке было написано, что нужен для работы сериализации
    public ResponseErrorBody() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
