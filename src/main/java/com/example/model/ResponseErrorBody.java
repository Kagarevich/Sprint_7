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

    //Тут надо руками перебирать, без каких методов тесты упадут, а без каких нет, поэтому не стал убирать - в теории
    //не написано почему некоторые геттеры/сеттеры нужны, а некоторые - нет
    public void setMessage(String message) {
        this.message = message;
    }
}
