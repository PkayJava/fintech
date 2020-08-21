package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.EntityStatusEnum;
import com.angkorteam.fintech.client.enums.EntityTypeEnum;

public class PostDatatableEntityCheckRequest {

    private String datatableName;

    private EntityTypeEnum entity;

    private long productId;

    private EntityStatusEnum status;

    private boolean systemDefined;

    public String getDatatableName() {
        return datatableName;
    }

    public void setDatatableName(String datatableName) {
        this.datatableName = datatableName;
    }

    public EntityTypeEnum getEntity() {
        return entity;
    }

    public void setEntity(EntityTypeEnum entity) {
        this.entity = entity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public EntityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EntityStatusEnum status) {
        this.status = status;
    }

    public boolean isSystemDefined() {
        return systemDefined;
    }

    public void setSystemDefined(boolean systemDefined) {
        this.systemDefined = systemDefined;
    }

}
