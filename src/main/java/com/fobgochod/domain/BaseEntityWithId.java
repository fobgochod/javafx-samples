package com.fobgochod.domain;

public abstract class BaseEntityWithId extends BaseEntity {

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}