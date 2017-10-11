package com.angkorteam.fintech.installation;

import java.io.Serializable;
import java.util.Map;

public class ErdVO implements Serializable {

    private String fieldName;

    private ErdRef referenceTo;

    private boolean pk = false;

    private boolean uq = false;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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

    public ErdRef getReferenceTo() {
        return referenceTo;
    }

    public void setReferenceTo(ErdRef referenceTo) {
        this.referenceTo = referenceTo;
    }

}
