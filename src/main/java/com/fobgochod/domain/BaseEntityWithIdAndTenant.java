package com.fobgochod.domain;

public class BaseEntityWithIdAndTenant extends BaseEntityWithTenant {

    private String id;

    public BaseEntityWithIdAndTenant() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
