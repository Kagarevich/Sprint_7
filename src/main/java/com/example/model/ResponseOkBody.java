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

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
