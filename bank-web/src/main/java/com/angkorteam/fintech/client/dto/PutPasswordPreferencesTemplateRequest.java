package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.ValidationPolicy;

public class PutPasswordPreferencesTemplateRequest {

    private ValidationPolicy validationPolicy;

    public ValidationPolicy getValidationPolicy() {
        return validationPolicy;
    }

    public void setValidationPolicy(ValidationPolicy validationPolicy) {
        this.validationPolicy = validationPolicy;
    }

}
