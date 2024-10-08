package com.example.model;

public class Courier {
    private Integer id;
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password) {
        this.id = null;
        this.login = login;
        this.password = password;
        this.firstName = null;
    }

    public Courier(String login, String password, String firstName) {
        this.id = null;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    //в уроке было написано, что нужен для работы сериализации
    public Courier() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    //Тут надо руками перебирать, без каких методов тесты упадут, а без каких нет, поэтому не стал убирать - в теории
    //не написано почему некоторые геттеры/сеттеры нужны, а некоторые - нет
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
