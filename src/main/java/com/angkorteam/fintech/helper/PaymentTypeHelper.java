package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class PaymentTypeHelper {

    private static final String CREATE_PAYMENTTYPE_URL = "/fineract-provider/api/v1/paymenttypes";
    private static final String PAYMENTTYPE_URL = "/fineract-provider/api/v1/paymenttypes";

    public static JsonNode create(JsonNode paymentType) throws UnirestException {
        return Helper.performServerPost(CREATE_PAYMENTTYPE_URL, paymentType);
    }

    public static JsonNode update(JsonNode paymentType) throws UnirestException {
        String id = (String) paymentType.getObject().remove("id");
        final String UPDATE_PAYMENTTYPE_URL = PAYMENTTYPE_URL + "/" + id;
        return Helper.performServerPut(UPDATE_PAYMENTTYPE_URL, paymentType);
    }

    public static JsonNode delete(String id) throws UnirestException {
        final String DELETE_PAYMENTTYPE_URL = PAYMENTTYPE_URL + "/" + id;
        return Helper.performServerDelete(DELETE_PAYMENTTYPE_URL);
    }

}
