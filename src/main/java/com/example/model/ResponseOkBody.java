package com.example.model;

public class ResponseOkBody {
    private Boolean ok;

    public ResponseOkBody(Boolean ok) {
        this.ok = ok;
    }

    //в уроке было написано, что нужен для работы сериализации
    public ResponseOkBody() {
    }

    public Boolean getOk() {
        return ok;
    }

    //Тут надо руками перебирать, без каких методов тесты упадут, а без каких нет, поэтому не стал убирать - в теории
    //не написано почему некоторые геттеры/сеттеры нужны, а некоторые - нет
    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
