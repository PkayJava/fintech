package com.angkorteam.fintech.dto.request;

import com.mashape.unirest.http.JsonNode;

import java.io.Serializable;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class FinancialActivityBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String financialActivityId;
    private boolean hasFinancialActivityId;

    private String glAccountId;
    private boolean hasGlAccountId;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasFinancialActivityId) {
            object.getObject().put("financialActivityId", this.financialActivityId);
        }
        if (this.hasGlAccountId) {
            object.getObject().put("glAccountId", this.glAccountId);
        }
        return object;
    }

    public FinancialActivityBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public FinancialActivityBuilder withFinancialActivityBuilder(String financialActivityId) {
        this.hasFinancialActivityId = true;
        this.financialActivityId = financialActivityId;
        return this;
    }

    public FinancialActivityBuilder withGlAccountId(String glAccountId) {
        this.hasGlAccountId = true;
        this.glAccountId = glAccountId;
        return this;
    }

}
