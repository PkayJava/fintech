package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.mashape.unirest.http.JsonNode;

public class MixedHelper {

    public static JsonNode create(IMifos session, JsonNode object) {
        String loanId = (String) object.getObject().remove("loanId");
        return Helper.performServerPost(session, "/api/v1/loanproducts/" + loanId + "/productmix", object);
    }

    public static JsonNode update(IMifos session, JsonNode object) {
        String loanId = (String) object.getObject().remove("loanId");
        return Helper.performServerPut(session, "/api/v1/loanproducts/" + loanId + "/productmix", object);
    }

}
