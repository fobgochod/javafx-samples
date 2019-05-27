package com.fobgochod.domain;

public class StatementTarget extends BaseEntity {

    private long statementSid;
    private long targetSid;
    private String type;
    private boolean cascade;

    public long getStatementSid() {
        return statementSid;
    }

    public void setStatementSid(long statementSid) {
        this.statementSid = statementSid;
    }

    public long getTargetSid() {
        return targetSid;
    }

    public void setTargetSid(long targetSid) {
        this.targetSid = targetSid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }
}
