package com.fobgochod.domain;

public class Statement extends BaseEntity {

    private String effect;
    private long policySid;

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public long getPolicySid() {
        return policySid;
    }

    public void setPolicySid(long policySid) {
        this.policySid = policySid;
    }


}
