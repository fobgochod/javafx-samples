package com.fobgochod.domain;

public class Policy extends BaseEntityWithIdAndTenant {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}