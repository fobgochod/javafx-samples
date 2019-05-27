package com.fobgochod.domain;

public class Role extends BaseEntityWithIdAndTenant {

    private String name;
    private int priority;
    private boolean readonly;
    private long roleCatalogSid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getRoleCatalogSid() {
        return roleCatalogSid;
    }

    public void setRoleCatalogSid(long roleCatalogSid) {
        this.roleCatalogSid = roleCatalogSid;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
}
