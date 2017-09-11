package com.angkorteam.fintech.dto.request;

import java.io.Serializable;

import com.angkorteam.fintech.dto.fixed.Attribute;
import com.angkorteam.fintech.dto.fixed.OperandType;
import com.angkorteam.fintech.dto.fixed.Operator;
import com.mashape.unirest.http.JsonNode;

public class IncentiveBuilder implements Serializable {

    private String entityType;
    private boolean hasEntityType;

    public IncentiveBuilder withEntityType(String entityType) {
	this.entityType = entityType;
	this.hasEntityType = true;
	return this;
    }

    private Attribute attributeName;
    private boolean hasAttributeName;

    public IncentiveBuilder withAttributeName(Attribute attributeName) {
	this.attributeName = attributeName;
	this.hasAttributeName = true;
	return this;
    }

    private Operator conditionType;
    private boolean hasConditionType;

    public IncentiveBuilder withConditionType(Operator conditionType) {
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

    private OperandType incentiveType;
    private boolean hasIncentiveType;

    public IncentiveBuilder withIncentiveType(OperandType incentiveType) {
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
	    if (this.incentiveType != null) {
		object.getObject().put("incentiveType", this.incentiveType.getLiteral());
	    } else {
		object.getObject().put("incentiveType", (String) null);
	    }
	}

	if (this.hasAttributeValue) {
	    object.getObject().put("attributeValue", this.attributeValue);
	}

	if (this.hasConditionType) {
	    if (this.conditionType != null) {
		object.getObject().put("conditionType", this.conditionType.getLiteral());
	    } else {
		object.getObject().put("conditionType", (String) null);
	    }
	}

	if (this.hasAttributeName) {
	    if (this.attributeName != null) {
		object.getObject().put("attributeName", this.attributeName.getLiteral());
	    } else {
		object.getObject().put("attributeName", (String) null);
	    }
	}

	if (this.hasEntityType) {
	    object.getObject().put("entityType", this.entityType);
	}
	return object;
    }

}
