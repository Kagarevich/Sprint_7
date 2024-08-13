package com.example.model;

public class PageInfo {
    private Integer page;
    private Integer total;
    private Integer limit;

    public PageInfo(Integer page, Integer total, Integer limit) {
        this.page = page;
        this.total = total;
        this.limit = limit;
    }

    //в уроке было написано, что нужен для работы сериализации
    public PageInfo() {
    }

    //Тут надо руками перебирать, без каких методов тесты упадут, а без каких нет, поэтому не стал убирать - в теории
    //не написано почему некоторые геттеры/сеттеры нужны, а некоторые - нет
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
