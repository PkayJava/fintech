package com.angkorteam.fintech.dto.builder;

import java.io.Serializable;

import io.github.openunirest.http.JsonNode;

public class ClientAddressBuilder implements Serializable {

    private String id;
    private boolean hasId;

    private String street;
    private boolean hasStreet;

    private String addressLine1;
    private boolean hasAddressLine1;

    private String addressLine2;
    private boolean hasAddressLine2;

    private String addressLine3;
    private boolean hasAddressLine3;

    private String city;
    private boolean hasCity;

    private String stateProvinceId;
    private boolean hasStateProvinceId;

    private String countryId;
    private boolean hasCountryId;

    private String postalCode;
    private boolean hasPostalCode;

    public JsonNode build() {
        JsonNode object = new com.angkorteam.fintech.dto.JsonNode();
        if (this.hasStreet) {
            object.getObject().put("street", this.street);
        }
        if (this.hasId) {
            object.getObject().put("id", this.id);
        }
        if (this.hasAddressLine1) {
            object.getObject().put("addressLine1", this.addressLine1);
        }
        if (this.hasAddressLine2) {
            object.getObject().put("addressLine2", this.addressLine2);
        }
        if (this.hasAddressLine3) {
            object.getObject().put("addressLine3", this.addressLine3);
        }
        if (this.hasCity) {
            object.getObject().put("city", this.city);
        }
        if (this.hasStateProvinceId) {
            object.getObject().put("stateProvinceId", this.stateProvinceId);
        }
        if (this.hasCountryId) {
            object.getObject().put("countryId", this.countryId);
        }
        if (this.hasPostalCode) {
            object.getObject().put("postalCode", this.postalCode);
        }
        return object;
    }

    public ClientAddressBuilder withId(String id) {
        this.id = id;
        this.hasId = true;
        return this;
    }

    public ClientAddressBuilder withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        this.hasPostalCode = true;
        return this;
    }

    public ClientAddressBuilder withCountryId(String countryId) {
        this.countryId = countryId;
        this.hasCountryId = true;
        return this;
    }

    public ClientAddressBuilder withStateProvinceId(String stateProvinceId) {
        this.stateProvinceId = stateProvinceId;
        this.hasStateProvinceId = true;
        return this;
    }

    public ClientAddressBuilder withCity(String city) {
        this.city = city;
        this.hasCity = true;
        return this;
    }

    public ClientAddressBuilder withAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
        this.hasAddressLine3 = true;
        return this;
    }

    public ClientAddressBuilder withAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        this.hasAddressLine2 = true;
        return this;
    }

    public ClientAddressBuilder withAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        this.hasAddressLine1 = true;
        return this;
    }

    public ClientAddressBuilder withStreet(String street) {
        this.street = street;
        this.hasStreet = true;
        return this;
    }

}
