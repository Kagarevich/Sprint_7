package com.example.model;

public class OrderList {
    private Order[] orders;
    private PageInfo pageInfo;
    private Station[] availableStations;

    public OrderList(Order[] orders, PageInfo pageInfo, Station[] availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    //в уроке было написано, что нужен для работы сериализации
    public OrderList() {
    }

    public Order[] getOrders() {
        return orders;
    }

    //Тут надо руками перебирать, без каких методов тесты упадут, а без каких нет, поэтому не стал убирать - в теории
    //не написано почему некоторые геттеры/сеттеры нужны, а некоторые - нет
    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Station[] getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(Station[] availableStations) {
        this.availableStations = availableStations;
    }
}
