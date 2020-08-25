package com.angkorteam.fintech.dto.builder.loan;

import io.github.openunirest.http.JsonNode;

public class LoanCollateralBuilder {

    private String loanId;
    private boolean hasLoanId;

    public LoanCollateralBuilder withLoanId(String loanId) {
        this.loanId = loanId;
        this.hasLoanId = true;
        return this;
    }

    private String locale = "en";
    private boolean hasLocale = true;

    public LoanCollateralBuilder withLocale(String locale) {
        this.locale = locale;
        this.hasLocale = true;
        return this;
    }

    private String collateralTypeId;
    private boolean hasCollateralTypeId;

    public LoanCollateralBuilder withCollateralTypeId(String collateralTypeId) {
        this.collateralTypeId = collateralTypeId;
        this.hasCollateralTypeId = true;
        return this;
    }

    private Double value;
    private boolean hasValue;

    public LoanCollateralBuilder withValue(Double value) {
        this.value = value;
        this.hasValue = true;
        return this;
    }

    private String description;
    private boolean hasDescription;

    public LoanCollateralBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasLoanId) {
            object.getObject().put("loanId", this.loanId);
        }

        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }

        if (this.hasValue) {
            object.getObject().put("value", this.value);
        }

        if (this.hasCollateralTypeId) {
            object.getObject().put("collateralTypeId", this.collateralTypeId);
        }

        if (this.hasLocale) {
            object.getObject().put("locale", this.locale);
        }
        return object;
    }

}
