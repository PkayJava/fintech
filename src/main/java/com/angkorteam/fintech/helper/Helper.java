package com.angkorteam.fintech.helper;

import com.angkorteam.fintech.IMifos;
import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.framework.SpringBean;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by socheatkhauv on 6/26/17.
 */
public class Helper {

    public static JsonNode performServerPost(IMifos session, String url, JsonNode body) throws UnirestException {
        return performServerPost(session.getIdentifier(), session.getToken(), url, body);
    }

    public static JsonNode performServerPost(String identifier, String token, String url, JsonNode body)
            throws UnirestException {
        MifosDataSourceManager mifos = SpringBean.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        return Unirest.post(mifosUrl).header("Authorization", "Basic " + token)
                .header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").body(body)
                .asJson().getBody();
    }

    public static JsonNode performServerPost(IMifos session, String url) throws UnirestException {
        return performServerPost(session.getIdentifier(), session.getToken(), url);
    }

    public static JsonNode performServerPost(String identifier, String token, String url) throws UnirestException {
        MifosDataSourceManager mifos = SpringBean.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        return Unirest.post(mifosUrl).header("Authorization", "Basic " + token)
                .header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").asJson()
                .getBody();
    }

    public static JsonNode performServerDelete(IMifos session, String url) throws UnirestException {
        return performServerDelete(session.getIdentifier(), session.getToken(), url);
    }

    public static JsonNode performServerDelete(String identifier, String token, String url) throws UnirestException {
        MifosDataSourceManager mifos = SpringBean.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        return Unirest.delete(mifosUrl).header("Authorization", "Basic " + token)
                .header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").asJson()
                .getBody();
    }

    public static JsonNode performServerPut(IMifos session, String url, JsonNode body) throws UnirestException {
        return performServerPut(session.getIdentifier(), session.getToken(), url, body);
    }

    public static JsonNode performServerPut(String identifier, String token, String url, JsonNode body)
            throws UnirestException {
        MifosDataSourceManager mifos = SpringBean.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl() + url;
        return Unirest.put(mifosUrl).header("Authorization", "Basic " + token)
                .header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").body(body)
                .asJson().getBody();
    }

}
