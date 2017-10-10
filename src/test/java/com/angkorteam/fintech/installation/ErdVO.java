package com.angkorteam.fintech.installation;

import java.io.Serializable;
import java.util.Map;

public class ErdVO implements Serializable {

    private String masterField;

    private Map<String, String> referenceBy;

    public String getMasterField() {
        return masterField;
    }

    public void setMasterField(String masterField) {
        this.masterField = masterField;
    }

    public Map<String, String> getReferenceBy() {
        return referenceBy;
    }

    public void setReferenceBy(Map<String, String> referenceBy) {
        this.referenceBy = referenceBy;
    }

}
