package com.fobgochod.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class BaseEntity implements Serializable {

    private long sid;
    private Long createBy;
    private Long createProvider;
    private Long createOrg;
    private LocalDateTime createDate;
    private Long modifyBy;
    private Long modifyProvider;
    private LocalDateTime modifyDate;
    private String hash;
    private boolean disabled;
    private boolean deleted;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateProvider() {
        return createProvider;
    }

    public void setCreateProvider(Long createProvider) {
        this.createProvider = createProvider;
    }

    public Long getCreateOrg() {
        return createOrg;
    }

    public void setCreateOrg(Long createOrg) {
        this.createOrg = createOrg;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Long getModifyProvider() {
        return modifyProvider;
    }

    public void setModifyProvider(Long modifyProvider) {
        this.modifyProvider = modifyProvider;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
