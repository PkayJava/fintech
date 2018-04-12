package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;

import com.angkorteam.fintech.dto.constant.FinancialActivityTypeEnum;
import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public class FinancialActivityBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private FinancialActivityTypeEnum financialActivityId;
    private boolean hasFinancialActivityId;

    private String glAccountId;
    private boolean hasGlAccountId;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasFinancialActivityId) {
            if (this.financialActivityId != null) {
                object.getObject().put("financialActivityId", this.financialActivityId.getLiteral());
            } else {
                object.getObject().put("financialActivityId", (String) null);
            }
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

    public FinancialActivityBuilder withFinancialActivityId(FinancialActivityTypeEnum financialActivityId) {
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
