package com.angkorteam.fintech.dto.request;

import java.io.Serializable;

import com.mashape.unirest.http.JsonNode;

public class IncentiveBuilder implements Serializable {

    private String entityType;
    private boolean hasEntityType;

    public IncentiveBuilder withEntityType(String entityType) {
        this.entityType = entityType;
        this.hasEntityType = true;
        return this;
    }

    private String attributeName;
    private boolean hasAttributeName;

    public IncentiveBuilder withAttributeName(String attributeName) {
        this.attributeName = attributeName;
        this.hasAttributeName = true;
        return this;
    }

    private String conditionType;
    private boolean hasConditionType;

    public IncentiveBuilder withConditionType(String conditionType) {
        this.conditionType = conditionType;
        this.hasConditionType = true;
        return this;
    }

    private String attributeValue;
    private boolean hasAttributeValue;

    public IncentiveBuilder withAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        this.hasAttributeValue = true;
        return this;
    }

    private String incentiveType;
    private boolean hasIncentiveType;

    public IncentiveBuilder withIncentiveType(String incentiveType) {
        this.incentiveType = incentiveType;
        this.hasIncentiveType = true;
        return this;
    }

    private Double amount;
    private boolean hasAmount;

    public IncentiveBuilder withAmount(Double amount) {
        this.amount = amount;
        this.hasAmount = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();

        if (this.hasAmount) {
            object.getObject().put("amount", this.amount);
        }

        if (this.hasIncentiveType) {
            object.getObject().put("incentiveType", this.incentiveType);
        }

        if (this.hasAttributeValue) {
            object.getObject().put("attributeValue", this.attributeValue);
        }

        if (this.hasConditionType) {
            object.getObject().put("conditionType", this.conditionType);
        }

        if (this.hasAttributeName) {
            object.getObject().put("attributeName", this.attributeName);
        }

        if (this.hasEntityType) {
            object.getObject().put("entityType", this.entityType);
        }
        return object;
    }

}
