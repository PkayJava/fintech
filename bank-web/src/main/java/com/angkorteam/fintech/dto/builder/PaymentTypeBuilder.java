package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;

import org.json.JSONObject;

import io.github.openunirest.http.JsonNode;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class PaymentTypeBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String name;
    private boolean hasName;

    private String description;
    private boolean hasDescription;

    private boolean cashPayment;
    private boolean hasCashPayment;

    private Long position;
    private boolean hasPosition;

    public PaymentTypeBuilder withCashPayment(boolean cashPayment) {
        this.cashPayment = cashPayment;
        this.hasCashPayment = true;
        return this;
    }

    public PaymentTypeBuilder withPosition(Long position) {
        this.position = position;
        this.hasPosition = true;
        return this;
    }

    public PaymentTypeBuilder withDescription(String description) {
        this.description = description;
        this.hasDescription = true;
        return this;
    }

    public PaymentTypeBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public PaymentTypeBuilder withName(String name) {
        this.name = name;
        this.hasName = true;
        return this;
    }

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode(new JSONObject());
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasCashPayment) {
            object.getObject().put("isCashPayment", this.cashPayment);
        }
        if (this.hasName) {
            object.getObject().put("name", this.name);
        }
        if (this.hasDescription) {
            object.getObject().put("description", this.description);
        }
        if (this.hasPosition) {
            object.getObject().put("position", this.position);
        }
        return object;
    }

}
