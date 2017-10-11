package com.angkorteam.fintech.installation;

import java.io.Serializable;

public class ErdRef implements Serializable {

    private String tableName;

    private String fieldName;

    private boolean pk = false;

    private boolean uq = false;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public boolean isUq() {
        return uq;
    }

    public void setUq(boolean uq) {
        this.uq = uq;
    }

}
