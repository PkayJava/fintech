package com.angkorteam.fintech.installation;

import java.io.Serializable;
import java.util.List;

public class ErdVO implements Serializable {

    private List<String> fieldName;

    private ErdRef referenceTo;

    public List<String> getFieldName() {
        return fieldName;
    }

    public void setFieldName(List<String> fieldName) {
        this.fieldName = fieldName;
    }

    public ErdRef getReferenceTo() {
        return referenceTo;
    }

    public void setReferenceTo(ErdRef referenceTo) {
        this.referenceTo = referenceTo;
    }

}
