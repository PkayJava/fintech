package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class FundHelper {

    private static final String FUNDS_URL = "/fineract-provider/api/v1/funds";
    private static final String CREATE_FUNDS_URL = FUNDS_URL;

    public static JsonNode createFund(JsonNode fund) throws UnirestException {
        return Helper.performServerPost(CREATE_FUNDS_URL, fund);
    }

    public static JsonNode updateFund(JsonNode fund) throws UnirestException {
        String fundID = (String) fund.getObject().remove("id");
        final String URL = FUNDS_URL + "/" + fundID;
        return Helper.performServerPut(URL, fund);
    }

}
