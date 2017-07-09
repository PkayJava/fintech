package com.angkorteam.fintech.helper;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/22/17.
 */
public class OfficeHelper {

    private static final String OFFICE_URL = "/fineract-provider/api/v1/offices";

//    private final RequestSpecification requestSpec;
//    private final ResponseSpecification responseSpec;

//    public OfficeHelper(final RequestSpecification requestSpec,
//                        final ResponseSpecification responseSpec) {
//        this.requestSpec = requestSpec;
//        this.responseSpec = responseSpec;
//    }

//    public OfficeDomain retrieveOfficeByID(int id) {
//        Object get = Utils.performServerGet(
//                requestSpec, responseSpec, OFFICE_URL + "/" + id + "?"
//                        + Utils.TENANT_IDENTIFIER, "");
//        final String json = new Gson().toJson(get);
//        return new Gson().fromJson(json, new TypeToken<OfficeDomain>() {
//        }.getType());
//    }

    public static JsonNode createOffice(JsonNode office) throws UnirestException {
        return Helper.performServerPost(OFFICE_URL, office);
    }

    public static JsonNode updateOffice(JsonNode office) throws UnirestException {
        String id = (String) office.getObject().remove("id");
        return Helper.performServerPut(OFFICE_URL + "/" + id, office);
    }
}
