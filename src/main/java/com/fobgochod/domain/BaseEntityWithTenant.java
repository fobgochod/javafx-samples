package com.fobgochod.domain;

public abstract class BaseEntityWithTenant extends BaseEntity {

    private long tenantSid;

    public BaseEntityWithTenant() {
    }

    public long getTenantSid() {
        return this.tenantSid;
    }

    public void setTenantSid(long tenantSid) {
        this.tenantSid = tenantSid;
    }
}
