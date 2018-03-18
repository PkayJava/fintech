package com.angkorteam.fintech.helper;

import org.apache.wicket.WicketRuntimeException;

import com.angkorteam.fintech.MifosDataSourceManager;
import com.angkorteam.framework.SpringBean;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class LoginHelper {

    public static JsonNode authenticate(String identifier, String username, String password) {
        MifosDataSourceManager mifos = SpringBean.getBean(MifosDataSourceManager.class);
        String mifosUrl = mifos.getMifosUrl();
        return authenticate(mifosUrl, identifier, username, password);
    }

    public static JsonNode authenticate(String mifosUrl, String identifier, String username, String password) {
        String url = mifosUrl + "/api/v1/authentication?username=" + username + "&password=" + password;
        try {
            return Unirest.post(url).header("Fineract-Platform-TenantId", identifier).header("Content-Type", "application/json").asJson().getBody();
        } catch (UnirestException e) {
            throw new WicketRuntimeException(e);
        }
    }

}
