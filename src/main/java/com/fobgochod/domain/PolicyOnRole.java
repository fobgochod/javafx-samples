package com.fobgochod.domain;

public class PolicyOnRole extends BaseEntity {

    private long roleSid;
    private long policySid;

    public long getRoleSid() {
        return roleSid;
    }

    public void setRoleSid(long roleSid) {
        this.roleSid = roleSid;
    }

    public long getPolicySid() {
        return policySid;
    }

    public void setPolicySid(long policySid) {
        this.policySid = policySid;
    }


}
